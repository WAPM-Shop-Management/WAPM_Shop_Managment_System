package spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.ItemDTO;
import spring.service.ItemService;
import spring.util.StandardResponse;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin
public class FileUploadController {

    private final ItemService itemService;

    public FileUploadController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Update Item Image - Base64
     *
     * @param itemId  ID of item which need to update
     * @param itemDTO Request Body
     */
    @PostMapping(path = "/item/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> updateItemImage(@PathVariable int itemId,
                                                            @RequestBody ItemDTO itemDTO) {

        itemDTO.setId(itemId);
        itemService.updateItemImage(itemDTO);
        return new ResponseEntity<>(new StandardResponse(200, "Success", null), HttpStatus.OK);
    }
}
