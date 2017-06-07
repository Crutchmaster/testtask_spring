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
import java.lang.Double;
import java.lang.Integer;
import java.util.Arrays;

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
    public String data(@PathVariable String type, Model model) {
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

    @RequestMapping(value = "/api/edititem", method = RequestMethod.POST)
    public String apiEditItem(@RequestParam("id") String id, 
                              @RequestParam("brandId") String brandId, 
                              @RequestParam("cost") String cost,
                              @RequestParam("count") String count,
                              @RequestParam("itemAttrId") String[] itemAttrId,
                              @RequestParam("attrValueStr") String[] attrValueStr,
                              @RequestParam("attrValue") String[] attrValueId) {
//        return "Id: "+id+" brandId: "+brandId+" cost: "+cost+" count: "+count+ " itemAttrId: "+Arrays.toString(itemAttrId)+
//                                  " attrValueStr:"+Arrays.toString(attrValueStr)+" attrValue "+Arrays.toString(attrValue);
        
        int len = itemAttrId.length;
        if (len!=attrValueStr.length || len!=attrValueId.length) return genError("Different attribute array size in POST");
        
        Long itemId;
        Long itemBrandId;
        try {
                itemId = Long.parseLong(id);
            } catch (Exception e) {
                return genError("Can't pasre id "+id+" as long");
            }
        try {
                itemBrandId = Long.parseLong(brandId);
            } catch (Exception e) {
                return genError("Can't pasre BrandId "+brandId+" as long");
            }

        Item item = itemsRepo.getOne(itemId);
        if (item == null) return genError("Can't find item with id "+id); 
        Double itemCost;
        try {
                itemCost = Double.parseDouble(cost);
            } catch (Exception e) {
                return genError("Can't parse cost "+cost+" as double");
            }
        int itemCount;
        try {
                itemCount = Integer.parseInt(count);
            } catch (Exception e) {
                return genError("Can't parse count "+count+" as int");
            }
        Brand itemBrand = brandsRepo.getOne(itemBrandId);
        if (itemBrand == null) return genError("Can't find brand with id "+itemBrandId);

        item.setBrand(itemBrand);
        item.setPrice(itemCost);
        item.setAmount(itemCount);

        for (int i = 0; i < len; i++) {
            Long idItemAttr, idAttrValue;
            String variable = "idItemAttr";
            try {
                idItemAttr = Long.parseLong(itemAttrId[i]);
                
                variable = "idAttrValue";
                idAttrValue = Long.parseLong(attrValueId[i]);
            } catch (Exception e) {
                return genError("Can't parse "+variable+" as long");
            }
            ItemAttr itemAttr = itemAttrsRepo.getOne(idItemAttr);
            if (itemAttr == null) return genError("Can't find item attributes with id "+itemAttrId[i]);
            AttrValue attrValue = attrValuesRepo.getOne(idAttrValue);
            if (attrValue == null) return genError("Can't find attribute value for id "+attrValueId[i]);
            if (item.getId() != itemAttr.getItem().getId() || attrValue.getAttr().getId() != itemAttr.getAttr().getId())
                return genError("Link error");
            itemAttr.setAttrValueStr(attrValueStr[i]);
            itemAttr.setAttrValue(attrValue);
            itemAttrsRepo.save(itemAttr);
        }

 

        itemsRepo.save(item);
        return item.toJSON();
        
    }

    private String genError(String text) {
        return "{\"error\":\""+text+"\"}";
    }

}
