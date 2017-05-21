package shop;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="items")
public class Item {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	private Set<ItemAttr> attrs;
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
    private String name;
	private Double price;
	private Long amount;
    protected Item() {}

    public Item(String name, Brand brand, Double price, Long amount) {
        this.name = name;
		this.brand = brand;
		this.price = price;
		this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format(
                "Item[id=%d, name='%s', brand_name='%s', price=%8.2f, amount=%d]",
                id, name, brand.getName(), price, amount);
    }
    
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Set<ItemAttr> getAttrValues() {
		return attrs;			
	}

}

