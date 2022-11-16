package converters;

import DTOs.StoreProductDTO;
import entities.StoreProduct;

public class StoreProductConverter {

    public static StoreProductDTO toDto(StoreProduct entity) {
        System.out.println("storeProduct to DTO");
        StoreProductDTO dto = new StoreProductDTO();

        dto.setProductId(entity.getId());
        dto.setPriceEur(entity.getPriceEur());
        dto.setPriceGbp(entity.getPriceGbp());
        dto.setorigPriceEur(entity.getOrigPriceEur());

        dto.setStoreId(entity.getStoreId());
        System.out.println(String.format("StoreId: {%d}", dto.getStoreId()));
        dto.setStoreTitle(entity.getStore().getTitle());

        dto.setProductId(entity.getProductId());
        dto.setProductTitle(entity.getProduct().getTitle());
        dto.setProductCategory(entity.getProduct().getCategory());
        dto.setProductFavourite(entity.getProduct().getFavourite());

        return dto;
    }

    public static StoreProduct toEntity(StoreProductDTO dto) {
        StoreProduct entity = new StoreProduct();

        entity.setId(dto.getId());
        entity.setPriceEur(dto.getPriceEur());
        entity.setPriceGbp(dto.getPriceGbp());
        entity.setStoreId(dto.getStoreId());
        entity.setProductId(dto.getProductId());
        entity.setOrigPriceEur(dto.getOrigPriceEur());

        return entity;
    }
}
