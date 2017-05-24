package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;

@RestController
public class ShopController {
    @Autowired
	TypesRepository typesRepo;
	@Autowired
	ItemsRepository itemsRepo;
    @RequestMapping("/")
    public String index() {
		String res = "<html>";
		for (Item i : itemsRepo.findAll()) {
			res += "<div>"+i.toString()+"</div>";
		}

		return res += "</html>";
    }

    @RequestMapping("/init")
    public String init() {
        typesRepo.save(new Type("int"));
        typesRepo.save(new Type("String"));
        typesRepo.save(new Type("bool"));
        return "OK";
    }

    @GetMapping("/view/{data}")
    public String view(@PathVariable String data) {
        String res = "<html>";
        JpaRepository<?, Long> repo = typesRepo;
        switch (data) {
            case "types" : repo = typesRepo; break;
        }

        for (Object obj : repo.findAll()) {
			res += "<div>"+obj.toString()+"</div>";
		}
        res += "</html>";
        return res;
    }
    
}
