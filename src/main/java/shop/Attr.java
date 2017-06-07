package shop;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="attrs")
public class Attr implements JSON {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
	@ManyToOne
	@JoinColumn(name = "type_id")
	private Type type;
	@OneToMany(mappedBy = "attr", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<ItemAttr> attrs;
    @OneToMany(mappedBy = "attr", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<AttrValue> attrValues;

    protected Attr() {}

    public Attr(String name, Type type) {
        this.name = name;
		this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Type[id=%d, name='%s', typename='%s']",
                id, name, type.getName());
    }

    public String toJSON() {
        String val = "[";
        boolean first = true;
        for (AttrValue i : attrValues) {
            val += first ? "" : ",";
            val += i.toJSON();
            first = false;
        }
        val += "]";

        return String.format(
                "{\"id\":\"%d\", \"name\":\"%s\", \"type\":%s, \"values\" : %s}",
                id, name, type.toJSON(), val);
   }


	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public Set<ItemAttr> getAttrValues() {
		return attrs;			
	}

    public Set<AttrValue> getAttrVariants() {
        return attrValues;
    }

}
