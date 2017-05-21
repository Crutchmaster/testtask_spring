package shop;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="attrs")
public class Attr {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
	@ManyToOne
	@JoinColumn(name = "type_id")
	private Type type;
	@OneToMany(mappedBy = "attr", cascade = CascadeType.ALL)
	private Set<ItemAttr> attrs;

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

}
