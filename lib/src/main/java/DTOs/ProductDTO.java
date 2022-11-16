package DTOs;

public class ProductDTO {
    private Integer productId;
    private String title;
    private String description;
    private String category; //coffe, bread, softDrink, rawMeat
    private boolean favourite;
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer id) {
        this.productId = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory(){return category;}
    public void setCategory(String cat){this.category = cat;}

    public boolean getFavourite(){return favourite;}
    public void setFavourite(boolean fav){this.favourite = fav;}
}
