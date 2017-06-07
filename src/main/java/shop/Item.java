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
    @ManyToOne
    @JoinColumn(name = "itemType_id")
    private ItemType itemType;
    private String name;
	private Double price;
	private int amount;
    protected Item() {}

    public Item(String name, ItemType itemType, Brand brand, Double price, int amount) {
        this.name = name;
        this.itemType = itemType;
		this.brand = brand;
		this.price = price;
		this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format(
                "Item[id=%d, name='%s', type='%s', brand_name='%s', price=%8.2f, amount=%d]",
                id, name, itemType.getName(), brand.getName(), price, amount);
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

    public ItemType getItemType() {
        return itemType;
    }

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Set<ItemAttr> getAttrValues() {
		return attrs;			
	}

    public String getPriceStr() {
        return String.format("%8.2f", price);
    }

    public String getAmountStr() {
        return String.format("%d", amount);
    }

}

