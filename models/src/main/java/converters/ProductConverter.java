package converters;

import DTOs.ProductDTO;
import entities.Product;

public class ProductConverter {
    public static ProductDTO toDto(Product entity) {
        ProductDTO dto = new ProductDTO();

        dto.setProductId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setFavourite(entity.getFavourite());

        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product entity = new Product();

        entity.setId(dto.getProductId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setFavourite(dto.getFavourite());

        return entity;
    }
}
