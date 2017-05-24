package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
public class ShopController {
    @Autowired
	TypesRepository typesRepo;
	ItemsRepository itemsRepo;
    AttrsRepository attrsRepo;
    AttrValuesRepository attrValuesRepo;
    BrandsRepository brandsRepo;
    ItemAttrsRepository ItemAttrsRepo;
    @RequestMapping("/")
    public String index(Model model) {
		return "main";
    }

    @RequestMapping("/init")
    public String init() {
        typesRepo.deleteAll();
        typesRepo.save(new Type("int"));
        typesRepo.save(new Type("string"));
        typesRepo.save(new Type("bool"));

        return "OK";
    }

    @GetMapping("/view/{data}")
    public String view(@PathVariable String data, Model model) {
        JpaRepository<?, Long> repo = itemsRepo;
        switch (data) {
            case "types" : repo = typesRepo; break;
            default : data = "items";
        }
        model.addAttribute("data", data);
        model.addAttribute("list", repo.findAll());
        return "list";
    }
    
}
