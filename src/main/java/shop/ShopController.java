package shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ShopController {
    @Autowired
	TypesRepository typesRepo;
    @RequestMapping("/")
    public String index() {
        //return "Greetings from Spring Boot!";
		String res = "<html>";
		for (Type t : typesRepo.findAll()) {
			res += "<div>"+t.toString()+"</div>";
		}
		return res += "</html>";
    }
    
}
