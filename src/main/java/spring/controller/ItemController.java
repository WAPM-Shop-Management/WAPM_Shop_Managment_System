/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.ItemDTO;
import spring.service.ItemService;
import spring.util.StandardResponse;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Get All Items
     *
     * @return ItemDto List
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> getAllItemList() {
        return new ResponseEntity<>(new StandardResponse(200, "The Item details have been successfully fetched.", itemService.getAllItem()),
                HttpStatus.OK);
    }

    /**
     * Get Details of Specific Item
     *
     * @param itemId ID of item which need to search
     * @return ItemDto
     */
    @GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> getItem(@PathVariable int itemId) {

        return new ResponseEntity<>(new StandardResponse(200, "The Item details have been successfully fetched.", itemService.getItem(itemId)), HttpStatus.OK);
    }

    /**
     * Save New Item
     *
     * @param itemDTO Request Body
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemDTO itemDTO) {

        int i = itemService.saveItem(itemDTO);
        return new ResponseEntity<>(new StandardResponse(200, "The Item has been successfully saved.", i), HttpStatus.OK);
    }

    /**
     * Update Details of Specific Item
     *
     * @param itemId  ID of item which need to update
     * @param itemDTO Request Body
     */
    @PutMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> updateItem(@PathVariable int itemId,
                                                       @RequestBody ItemDTO itemDTO) {

        itemDTO.setId(itemId);
        itemService.updateItem(itemDTO);
        return new ResponseEntity<>(new StandardResponse(200, "The Item details have been successfully updated.", null), HttpStatus.OK);
    }

    /**
     * Update stock quantity of Specific Item
     *
     * @param itemId  ID of item which need to update
     * @param itemDTO Request Body
     */
    @PatchMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> updateStock(@PathVariable int itemId,
                                                        @RequestBody ItemDTO itemDTO) {

        itemDTO.setId(itemId);
        itemService.updateStock(itemDTO);
        return new ResponseEntity<>(new StandardResponse(200, "The Item stock has been successfully updated.", null), HttpStatus.OK);
    }
}
