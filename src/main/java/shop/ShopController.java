package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.lang.Long;

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
    @Autowired
    ItemTypesRepository itemTypesRepo;
    @RequestMapping("/")
    public String index(Model model) {
		return "main";
    }

    @RequestMapping("/init")
    public String init(Model model) {
        itemAttrsRepo.deleteAll();
        itemsRepo.deleteAll();
        brandsRepo.deleteAll();
        attrValuesRepo.deleteAll();
        attrsRepo.deleteAll();
        typesRepo.deleteAll();

        ObjectMapper mapper = new ObjectMapper();
        File f = new File("data.json");

        HashMap<String, Object> root;
        try {
            root = mapper.readValue(
                    getClass().getResourceAsStream("/init/data.json"),
                    new TypeReference<HashMap<String, Object>>(){});
        } catch (Exception e) {
            model.addAttribute("debug", e.getMessage());
            return "debug";
        }

        List types = (List) root.get("types");

        for (Object s : types) {
            typesRepo.save(new Type(s.toString()));
        }
        List itemTypes = (List) root.get("itemTypes");

        for (Object s : itemTypes) {
            itemTypesRepo.save(new ItemType(s.toString()));
        }

        HashMap<String, AttrValue> attrValuesHash = new HashMap<String, AttrValue>();
        List attrs = (List) root.get("attrs");
        for (Object a : attrs) {
            HashMap<String, Object> m = (HashMap<String,Object>)a;
            Attr attr = new Attr(m.get("name").toString(), 
                                typesRepo.findByName(m.get("type").toString()).get(0));
            attrsRepo.save(attr);
            List values = (List) m.get("values");
            for (Object v : values) {
                AttrValue av = new AttrValue(v.toString(), attr);
                attrValuesRepo.save(av);
                attrValuesHash.put(attr.getName()+":"+av.getValue(),av);
            }
        }

        List brands = (List) root.get("brands");

        for (Object s : brands) {
            brandsRepo.save(new Brand(s.toString()));
        }

        List items = (List) root.get("items");

        for (Object it : items) {
            HashMap<String,Object> itemJson = (HashMap<String,Object>)it;
            Item item = new Item(
                    itemJson.get("name").toString(),
                    itemTypesRepo.findByName(itemJson.get("itemType").toString()).get(0),
                    brandsRepo.findByName(itemJson.get("brand").toString()).get(0),
                    (Double)itemJson.get("cost"),
                    (int)itemJson.get("count")
                    );
            itemsRepo.save(item);
            List attrsList = (List) itemJson.get("attrs");
            for (Object attr : attrsList) {
                HashMap<String,Object> attrsJson = (HashMap<String,Object>)attr;
                Object cntObj = attrsJson.get("count");
                String cnt = cntObj == null ? "" : cntObj.toString();
                itemAttrsRepo.save(new ItemAttr(
                            cnt,
                            item,
                            attrsRepo.findByName(attrsJson.get("name").toString()).get(0),
                            attrValuesHash.get(attrsJson.get("name").toString()+":"+attrsJson.get("value").toString())
                            ));

            }
        }

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
        repos.add(itemTypesRepo);

        String[] keys = {"types","attrs","attrValues","brands","items","itemAttrs","itemTypes"};
        JpaRepository<?, Long> repo = itemsRepo;

        for (int i = 0; i < keys.length; i++) {
            if (data.equals(keys[i])) repo=repos.get(i);
        }

        model.addAttribute("data", data);
        model.addAttribute("list", repo.findAll());
        return "list";
    }

    @GetMapping("/debug")
    public String debug(Model model) {
        Type testType = typesRepo.findAll().get(0);
        ObjectMapper mapper = new ObjectMapper();
        String debug;
        try {
            debug = mapper.writeValueAsString(testType);
            List<HashMap<String,Object>> jsonType = mapper.readValue("[{\"id\":500,\"name\":\"list\"},{\"id\":501,\"name\":[\"array\",\"list\"]}]", new TypeReference<List<HashMap<String,Object>>>(){});
            debug += jsonType.get(0).get("name").toString();
            List lst = (List) jsonType.get(1).get("name");
            debug += lst.get(1).toString();

        } catch (Exception e) {
            debug = "IO Exception!"+e.getStackTrace();
        }
        model.addAttribute("debug", debug);
        return "debug";
    }

    @GetMapping("/items")
    public String items(Model model) {
        List<ItemType> itemTypesList = itemTypesRepo.findAll();
        model.addAttribute("list", itemTypesList);
        model.addAttribute("data", "Items list");
        return "items";
    }
    @GetMapping("/item/{id}")
    public String item(@PathVariable String id, Model model) {
        long itemId = 0;
        try {
            itemId = Long.parseLong(id);
        } catch (Exception e) {
            model.addAttribute("error", "Item id "+id+" is not long!");
            return "apperror";
        }
        Item i = itemsRepo.getOne(itemId);
        if (i == null) {
            model.addAttribute("error", "Item id "+id+" not found.");
            return "apperror";
        }
        model.addAttribute("data", i.getName());
        model.addAttribute("i", i);
        return "item";
    }

    @GetMapping("/edititem/{id}")
    public String edititem(@PathVariable String id, Model model) {
        long itemId = 0;
        try {
            itemId = Long.parseLong(id);
        } catch (Exception e) {
            model.addAttribute("error", "Item id "+id+" is not long!");
            return "apperror";
        }
        Item i = itemsRepo.getOne(itemId);
        if (i == null) {
            model.addAttribute("error", "Item id "+id+" not found.");
            return "apperror";
        }
        model.addAttribute("data", i.getName());
        model.addAttribute("i", i);
        model.addAttribute("brands", brandsRepo.findAll());
        return "edititem";
    }
}
