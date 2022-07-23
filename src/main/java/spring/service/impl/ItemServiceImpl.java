/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.ItemDTO;
import spring.entity.Item;
import spring.repo.ItemRepo;
import spring.service.ItemService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ItemDTO> getAllItem() {
        List<Item> itemList = itemRepo.findAll();
        return mapper.map(itemList,new TypeToken<List<ItemDTO>>(){}.getType());
    }
}
