package shop;

import javax.persistence.*;

@Entity
@Table(name="Types")
public class Type implements JSON {

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

}

