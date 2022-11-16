package entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "Store")
@NamedQueries(value =
{
        @NamedQuery(name = "Store.getAll",
                query = "SELECT s FROM Store s")
})
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    public ArrayList<StoreProduct> storeProducts = new ArrayList<>();

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<StoreProduct> getStoreProducts() {return storeProducts;}
    public void setStoreProducts(ArrayList<StoreProduct> storeProducts){this.storeProducts = storeProducts;}
    public void addStoreProduct(StoreProduct sp){this.storeProducts.add(sp);}
}
