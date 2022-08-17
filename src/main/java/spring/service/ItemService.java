package spring.service;

import spring.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItem();

    int saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO itemDTO);

    ItemDTO getItem(int itemId);

    void updateStock(ItemDTO itemDTO);

    void updateItemImage(ItemDTO itemDTO);


}
