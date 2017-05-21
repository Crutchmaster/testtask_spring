package shop;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name="brands")
public class Brand {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
	private Set<Item> items;

    protected Brand() {}

    public Brand(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Brand[id=%d, name='%s']",
                id, name);
    }
    
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<Item> getItems() {
		return items;
	}
}

