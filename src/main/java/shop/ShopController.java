package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
}
