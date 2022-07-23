/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.dto.ItemDTO;
import spring.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping(path = "")
    public List<ItemDTO> getAllItemList(){
        List<ItemDTO> itemList = itemService.getAllItem();
        return itemList;
    }
}
