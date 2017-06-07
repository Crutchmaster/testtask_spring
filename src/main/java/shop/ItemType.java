package shop;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="ItemTypes")
public class ItemType implements JSON {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "itemType", cascade = CascadeType.ALL)
    private Set<Item> items;

    protected ItemType() {}

    public ItemType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "ItemType[id=%d, name='%s']",
                id, name);
    }

    public String toJSON() {
        return String.format(
                "{\"id\":\"%d\",\"name\":\"%s\"}",
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

