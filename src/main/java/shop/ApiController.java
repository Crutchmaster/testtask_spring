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

@RestController
public class ApiController {
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

    @RequestMapping("/api/data/{type}")
    public String index(@PathVariable String type, Model model) {
        ArrayList<JpaRepository<?, Long>> repos = new ArrayList<JpaRepository<?, Long>>();
        repos.add(typesRepo);
        repos.add(attrsRepo);
        repos.add(attrValuesRepo);
        repos.add(brandsRepo);
        repos.add(itemsRepo);
        repos.add(itemTypesRepo);

        String[] keys = {"types","attrs","attrValues","brands","items","itemTypes"};
        JpaRepository<?, Long> repo = itemTypesRepo;
        for (int i = 0; i < keys.length; i++) {
            if (type.equals(keys[i])) repo=repos.get(i);
        }


        String ret = "[";
        boolean first = true;
        for (Object i : repo.findAll()) {
            ret += first ? "" : ",";
            JSON j = (JSON)(i);
            ret += j.toJSON();
            first = false; 
        }        
        ret += "]";

		return ret;
    }

}
