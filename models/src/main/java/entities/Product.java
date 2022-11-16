package entities;


import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "Product")
@NamedQueries(value =
{
        @NamedQuery(name = "Product.getAll",
                query = "SELECT p FROM Product p")
})
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "title")
        private String title;

        @Column(name = "description")
        private String description;

        @Column(name = "category")
        private String category; //coffe, bread, softDrink, rawMeat

        @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
        public ArrayList<StoreProduct> storeProducts = new ArrayList<>();

        @Column(name = "favourite")
        private boolean favourite;

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
