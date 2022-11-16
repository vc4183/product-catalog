package services.beans;


import DTOs.ProductDTO;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import converters.ProductConverter;
import entities.Product;

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
public class ProductMetadataBean {

    private Logger log = Logger.getLogger(ProductMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public List<ProductDTO> getProducts() {

        TypedQuery<Product> query = em.createNamedQuery(
                "Product.getAll", Product.class);

        List<Product> resultList = query.getResultList();

        return resultList.stream().map(ProductConverter::toDto).collect(Collectors.toList());

    }

    public List<ProductDTO> getProductsFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Product.class, queryParameters).stream()
                .map(ProductConverter::toDto).collect(Collectors.toList());
    }

    public ProductDTO getProduct(Integer id) {

        Product productEntity = em.find(Product.class, id);

        if (productEntity == null) {
            throw new NotFoundException();
        }

        ProductDTO dto = ProductConverter.toDto(productEntity);

        return dto;
    }

    public ProductDTO createProduct(ProductDTO dto) {

        Product pdo = ProductConverter.toEntity(dto);

        try {
            beginTx();
            em.persist(pdo);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (pdo.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ProductConverter.toDto(pdo);
    }

    public ProductDTO putProduct(Integer id, ProductDTO dto) {

        Product pdo = em.find(Product.class, id);

        if (pdo == null)
            return null;


        Product updatedProduct = ProductConverter.toEntity(dto);

        try {
            beginTx();
            updatedProduct.setId(pdo.getId());
            updatedProduct = em.merge(updatedProduct);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ProductConverter.toDto(updatedProduct);
    }

    public boolean deleteProduct(Integer id) {

        Product pdo = em.find(Product.class, id);

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
        else {
            return false;
        }

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
