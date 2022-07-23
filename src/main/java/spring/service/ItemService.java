package spring.service;

import spring.dto.ItemDTO;
import spring.entity.Item;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItem();
}
