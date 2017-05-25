package shop;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="attrs_values")
public class AttrValue {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String value;
	@ManyToOne
	@JoinColumn(name = "attr_id")
	private Attr attr;
	@OneToMany(mappedBy = "attrValue", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<ItemAttr> attrs;


    protected AttrValue() {}

    public AttrValue(String value, Attr attr) {
        this.value = value;
		this.attr = attr;
    }

    @Override
    public String toString() {
        return String.format(
                "Type[id=%d, value='%s', attrName='%s']",
                id, value, attr.getName());
    }


	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public Attr getAttr() {
		return attr;
	}

	public Set<ItemAttr> getAttrValues() {
		return attrs;			
	}

}

