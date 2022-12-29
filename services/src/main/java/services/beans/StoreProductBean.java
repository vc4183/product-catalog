package services.beans;

import DTOs.StoreProductDTO;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import converters.StoreProductConverter;
import entities.StoreProduct;

import org.eclipse.microprofile.metrics.annotation.Timed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
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

    @Timed
    public List<StoreProductDTO> getFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, StoreProduct.class, queryParameters).stream()
                .map(StoreProductConverter::toDto).collect(Collectors.toList());
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
