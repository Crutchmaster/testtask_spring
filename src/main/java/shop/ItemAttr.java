package shop;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="items_attrs")
public class ItemAttr {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_rec")
    private Long idRec;

	@ManyToOne
	@JoinColumn(name = "item_id")
    private Item item;
	@ManyToOne
	@JoinColumn(name = "attr_id")
	private Attr attr;
	@ManyToOne
	@JoinColumn(name = "attr_value_id")
	private AttrValue attrValue;

	private String attrValueStr;

    protected ItemAttr() {}

    public ItemAttr(String attrValueStr, Item item, Attr attr, AttrValue attrValue) {
        this.attrValueStr = attrValueStr;
		this.item = item;
		this.attr = attr;
		this.attrValue = attrValue;
    }

    @Override
    public String toString() {
        return String.format(
                "Attributes:[item_name=%s, attr_name='%s', attr_value='%s', attr_value_str='%s']",
                item.getName(),
                attr.getName(),
                attrValue.getValue(),
                attrValueStr);
    }

	public Long getIdRec() {
		return idRec;
	}
	public Item getItem() {
		return item;
	}

	public Attr getAttr() {
		return attr;
	}

	public AttrValue getAttrValue() {
		return attrValue;
	}

	public String getAttrValueStr() {
		return attrValueStr;
	}

    public String getAttrValueText() {
        return attrValueStr+" "+attrValue.getValue();
    }

}

