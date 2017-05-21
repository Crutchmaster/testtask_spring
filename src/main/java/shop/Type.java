// tag::sample[]
package shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="Types")
public class Type {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

    protected Type() {}

    public Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Type[id=%d, name='%s']",
                id, name);
    }

// end::sample[]

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}

