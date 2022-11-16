package DTOs;

public class StoreProductDTO {
    private Integer id;

    private Float priceEur;
    private Float priceGbp;

    private boolean origPriceEur;

    //STORE
    private Integer storeId;
    private String storeTitle;

    //PRODUCT
    private Integer productId;
    private String productTitle;
    private String productCategory;
    private boolean productFavourite;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public float getPriceEur(){return priceEur;}
    public void setPriceEur(float priceEur){this.priceEur = priceEur;}
    public float getPriceGbp(){return this.priceGbp;}
    public void setPriceGbp(float priceGbp){this.priceGbp = priceGbp;}

    public boolean getOrigPriceEur(){return this.origPriceEur;}
    public void setorigPriceEur(boolean ope){this.origPriceEur = ope;}

    //STORE
    public Integer getStoreId() {
        return storeId;
    }
    public void setStoreId(Integer sId) {
        this.storeId = sId;
    }
    public String getStoreTitle() {
        return storeTitle;
    }
    public void setStoreTitle(String title) {
        this.storeTitle = title;
    }

    //PRODUCT
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer prId) {
        this.productId = prId;
    }
    public String getProductTitle() {
        return productTitle;
    }
    public void setProductTitle(String prTitle) {
        this.productTitle = prTitle;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(String prCategory) {
        this.productCategory = prCategory;
    }
    public boolean getProductFavourite(){return productFavourite;}
    public void setProductFavourite(boolean productFavourite){this.productFavourite = productFavourite;}
}
