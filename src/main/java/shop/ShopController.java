package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import java.util.HashMap;
import java.util.ArrayList;

@Controller
public class ShopController {
    @Autowired
	TypesRepository typesRepo;
    @Autowired
	ItemsRepository itemsRepo;
    @Autowired
    AttrsRepository attrsRepo;
    @Autowired
    AttrValuesRepository attrValuesRepo;
    @Autowired
    BrandsRepository brandsRepo;
    @Autowired
    ItemAttrsRepository itemAttrsRepo;
    @RequestMapping("/")
    public String index(Model model) {
		return "main";
    }

    @RequestMapping("/init")
    public String init() {
        itemAttrsRepo.deleteAll();
        itemsRepo.deleteAll();
        brandsRepo.deleteAll();
        attrValuesRepo.deleteAll();
        attrsRepo.deleteAll();
        typesRepo.deleteAll();

        HashMap<String, Type> types = new HashMap<String, Type>();
        String typeNames[] = {"int","float","bool","string"};
        for (String s : typeNames) {
            types.put(s, new Type(s));
        }

        typesRepo.save(types.values());

        HashMap<String, Attr> attrs = new HashMap<String, Attr>();

        String attrNames[] = {"diag" ,"thickness","matrix_type","cryocam","width","height","length","loading"};
        String attrTypes[] = {"float","int"      ,"string"    ,"bool"   ,"int"  ,"int"   ,"int"   ,"string" };
        for (int i = 0; i < attrNames.length; i++) {
            attrs.put(attrNames[i], new Attr(attrNames[i], types.get(attrTypes[i])));
        }
        attrsRepo.save(attrs.values());

        HashMap<String, AttrValue> attrValues = new HashMap<String, AttrValue>();
        String attrValuesStr[][] = {
            {"inch"}, //diag
            {"mm"}, //thickness
            {"plasma","led"}, //matrix_type
            {"yes/no"}, //cryocam
            {"mm"},{"mm"},{"mm"}, //width,height,length
            {"vertical","horisontal"}}; //loading
        for (int i = 0; i < attrNames.length; i++) {
            for (String attrValue : attrValuesStr[i]) {
                attrValues.put(attrNames[i]+":"+attrValue, new AttrValue(attrValue, attrs.get(attrNames[i])));
            }
        }
        attrValuesRepo.save(attrValues.values());

        HashMap<String, Brand> brands = new HashMap<String, Brand>();
        String brandNames[] = {"LG","Samsung","Indesit"};
        for (String s : brandNames) {
            brands.put(s, new Brand(s));
        }
        brandsRepo.save(brands.values());

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(itemsRepo.save(new Item("Samsung UE32J4000AK",brands.get("Samsung"),15500.00,5)));
        items.add(itemsRepo.save(new Item("LG 43UH619V",brands.get("LG"),31000.00,3)));
        items.add(itemsRepo.save(new Item("Indesit GF280A",brands.get("Indesit"),16000.00,8)));
        items.add(itemsRepo.save(new Item("LG EA1500C",brands.get("LG"),22000.00,4)));
        items.add(itemsRepo.save(new Item("Indesit RC15V",brands.get("Indesit"),12000.00,5)));
        items.add(itemsRepo.save(new Item("Samsung AE335H",brands.get("Samsung"),16000.00,2)));

        itemAttrsRepo.save(new ItemAttr("40", items.get(0), attrs.get("diag"), attrValues.get("diag:inch")));
        itemAttrsRepo.save(new ItemAttr("5", items.get(0), attrs.get("thickness"), attrValues.get("thickness:mm")));
        itemAttrsRepo.save(new ItemAttr("", items.get(0), attrs.get("matrix_type"), attrValues.get("matrix_type:led")));
        itemAttrsRepo.save(new ItemAttr("32", items.get(1), attrs.get("diag"), attrValues.get("diag:inch")));
        itemAttrsRepo.save(new ItemAttr("4", items.get(1), attrs.get("thickness"), attrValues.get("thickness:mm")));
        itemAttrsRepo.save(new ItemAttr("", items.get(1), attrs.get("matrix_type"), attrValues.get("matrix_type:plasma")));

        itemAttrsRepo.save(new ItemAttr("no", items.get(2), attrs.get("cryocam"), attrValues.get("cryocam:yes/no")));
        itemAttrsRepo.save(new ItemAttr("yes", items.get(3), attrs.get("cryocam"), attrValues.get("cryocam:yes/no")));

        itemAttrsRepo.save(new ItemAttr("450", items.get(4), attrs.get("length"), attrValues.get("length:mm")));
        itemAttrsRepo.save(new ItemAttr("850", items.get(4), attrs.get("height"), attrValues.get("height:mm")));
        itemAttrsRepo.save(new ItemAttr("600", items.get(4), attrs.get("width"), attrValues.get("width:mm")));
        itemAttrsRepo.save(new ItemAttr("", items.get(4), attrs.get("loading"), attrValues.get("loading:vertical")));


        itemAttrsRepo.save(new ItemAttr("400", items.get(5), attrs.get("length"), attrValues.get("length:mm")));
        itemAttrsRepo.save(new ItemAttr("820", items.get(5), attrs.get("height"), attrValues.get("height:mm")));
        itemAttrsRepo.save(new ItemAttr("640", items.get(5), attrs.get("width"), attrValues.get("width:mm")));
        itemAttrsRepo.save(new ItemAttr("", items.get(5), attrs.get("loading"), attrValues.get("loading:horisontal")));

        return "main";
    }


    @GetMapping("/view/{data}")
    public String view(@PathVariable String data, Model model) {
        ArrayList<JpaRepository<?, Long>> repos = new ArrayList<JpaRepository<?, Long>>();
        repos.add(typesRepo);
        repos.add(attrsRepo);
        repos.add(attrValuesRepo);
        repos.add(brandsRepo);
        repos.add(itemsRepo);
        repos.add(itemAttrsRepo);

        String[] keys = {"types","attrs","attrValues","brands","items","itemAttrs"};
        JpaRepository<?, Long> repo = itemsRepo;

        boolean set = false;
        for (int i = 0; i < keys.length; i++) {
            if (data.equals(keys[i])) repo=repos.get(i);
        }

        model.addAttribute("data", data);
        model.addAttribute("list", repo.findAll());
        return "list";
    }
    
}
