package spring.service;

import spring.dto.ItemDTO;
import spring.entity.Item;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItem();

    void saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO itemDTO);

    ItemDTO getItem(int itemId);

    void updateStock(ItemDTO itemDTO);

    void updateItemImage(ItemDTO itemDTO);


}
