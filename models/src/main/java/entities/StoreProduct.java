package entities;

import javax.persistence.*;

@Entity
@Table(name = "StoreProduct")
@NamedQueries(value =
{
        @NamedQuery(name = "StoreProducts.getAll",
                query = "SELECT sp FROM StoreProduct sp")
})
public class StoreProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    //@JoinColumn(name ="store_id")
    @PrimaryKeyJoinColumn(name="store_id", referencedColumnName="id")
    private Store store;

    @Column(name = "store_id")
    private Integer storeId;


    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    //@JoinColumn(name ="product_id")
    @PrimaryKeyJoinColumn(name="product_id", referencedColumnName="id")
    private Product product;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "eur")
    private Float priceEur;

    @Column(name = "gbp")
    private Float priceGbp;

    @Column(name = "origPriceEur", columnDefinition = "boolean default true")
    private boolean origPriceEur; // ali je bila originalna cena v evrih

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Store getStore(){return this.store;}
    public void setStore(Store s){this.store = s;}
    public Integer getStoreId() {
        return this.storeId;
    }
    public void setStoreId(Integer storeId) {this.storeId = storeId;}


    public Product getProduct(){return this.product;}
    public void setProduct(Product p){this.product = p;}
    public Integer getProductId() {
        return this.productId;
    }
    public void setProductId(Integer prodId) {
        this.productId = prodId;
    }

    public float getPriceEur(){return this.priceEur;}
    public void setPriceEur(float price){this.priceEur = price;}

    public float getPriceGbp(){return this.priceGbp;}
    public void setPriceGbp(float price){this.priceGbp = price;}

    public boolean getOrigPriceEur(){return this.origPriceEur;}
    public void setOrigPriceEur(boolean ope){this.origPriceEur = ope;}


}
