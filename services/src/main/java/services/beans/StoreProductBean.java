package services.beans;

import DTOs.CurrencyConverterResponseDTO;
import DTOs.StoreProductDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import converters.StoreProductConverter;
import entities.StoreProduct;

import org.eclipse.microprofile.metrics.annotation.Counted;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class StoreProductBean {

    private Logger log = Logger.getLogger(StoreProductBean.class.getName());

    @Inject
    private EntityManager em;

    public List<StoreProductDTO> getAll() {

        TypedQuery<StoreProduct> query = em.createNamedQuery(
                "StoreProducts.getAll", StoreProduct.class);

        List<StoreProduct> resultList = query.getResultList();

        return resultList.stream().map(StoreProductConverter::toDto).collect(Collectors.toList());
    }

    @Counted(name = "get_filtered_storeProducts", absolute = true) //, absolute = true
    public List<StoreProductDTO> getFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        List<StoreProductDTO> dtos = JPAUtils.queryEntities(em, StoreProduct.class, queryParameters).stream()
                .map(StoreProductConverter::toDto).collect(Collectors.toList());

        float exchange = 0.0f;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.241.254.139/currency-convert/v1/exchange-rates/eur-to-gbp"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String res = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CurrencyConverterResponseDTO resultObject = null;
        try {
            resultObject = objectMapper.readValue(res, CurrencyConverterResponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        exchange = resultObject.getExchangeRate();

        if(exchange != 0.0f){
            for (StoreProductDTO dto:dtos) {
                if(dto.getOrigPriceEur()) //set GBP price
                    dto.setPriceGbp(dto.getPriceEur()*exchange);
                else //set eur price
                    dto.setPriceEur(dto.getPriceGbp() / exchange);
            }
        }

        return dtos;
    }

    public StoreProductDTO getById(Integer id) {

        StoreProduct storeProductEntity = em.find(StoreProduct.class, id);

        if (storeProductEntity == null) {
            throw new NotFoundException();
        }

        StoreProductDTO dto = StoreProductConverter.toDto(storeProductEntity);

        return dto;
    }

    public StoreProductDTO create(StoreProductDTO dto) {

        StoreProduct pdo = StoreProductConverter.toEntity(dto);

        try {
            beginTx();
            em.persist(pdo);
            em.flush();
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (pdo.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return getById(pdo.getId());
    }

    public StoreProductDTO put(Integer id, StoreProductDTO dto) {

        StoreProduct pdo = em.find(StoreProduct.class, id);

        if (pdo == null)
            return null;

        StoreProduct updatedPDO = StoreProductConverter.toEntity(dto);

        try {
            beginTx();
            updatedPDO.setId(pdo.getId());
            updatedPDO = em.merge(updatedPDO);
            em.merge(updatedPDO);
            em.flush();
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return StoreProductConverter.toDto(updatedPDO);
    }

    public boolean delete(Integer id) {
        StoreProduct pdo = em.find(StoreProduct.class, id);

        if (pdo != null) {
            try {
                beginTx();
                em.remove(pdo);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
