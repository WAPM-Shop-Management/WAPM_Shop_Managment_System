/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import spring.dto.ItemDTO;
import spring.entity.Item;
import spring.exception.CustomException;
import spring.repo.ItemRepo;
import spring.service.ItemService;
import spring.util.FileUploader;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static spring.constant.ApplicationConstant.RESOURCE_NOT_FOUND;

@Service
@Transactional
@Log4j2
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;
    private final ModelMapper mapper;
    private final FileUploader fileUploader;

    public ItemServiceImpl(ItemRepo itemRepo, ModelMapper mapper, FileUploader fileUploader) {
        this.itemRepo = itemRepo;
        this.mapper = mapper;
        this.fileUploader = fileUploader;
    }

    @Override
    public List<ItemDTO> getAllItem() {
        List<Item> itemList = itemRepo.findAll();
        return mapper.map(itemList, new TypeToken<List<ItemDTO>>() {
        }.getType());
    }

    @Override
    public void saveItem(ItemDTO itemDTO) {
        try {
            log.info("Execute method saveItem params {}", itemDTO);

            Item itemEntity = mapper.map(itemDTO, Item.class);
            itemRepo.save(itemEntity);

        } catch (Exception e) {
            log.error("Method saveItem : ", e);
            throw e;
        }
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        try {
            log.info("Execute method updateItem params {}", itemDTO);

            //check item available or not
            Optional<Item> itemOptional = itemRepo.findById(itemDTO.getId());
            itemOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Item details not found"));

            Item item = itemOptional.get();
            item.setQty(itemDTO.getQty());
            item.setItemName(itemDTO.getItemName());
            item.setPrice(itemDTO.getPrice());

            itemRepo.save(item);

        } catch (Exception e) {
            log.error("Method updateItem : ", e);
            throw e;
        }
    }

    @Override
    public ItemDTO getItem(int itemId) {
        try {
            log.info("Execute method getItem params {}", itemId);

            //check item available or not
            Optional<Item> itemOptional = itemRepo.findById(itemId);
            itemOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Item details not found"));

            return mapper.map(itemOptional.get(), ItemDTO.class);
        } catch (Exception e) {
            log.error("Method getItem : ", e);
            throw e;
        }
    }

    @Override
    public void updateStock(ItemDTO itemDTO) {
        try {
            log.info("Execute method updateStock params {}", itemDTO);

            //check item available or not
            Optional<Item> itemOptional = itemRepo.findById(itemDTO.getId());
            itemOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Item details not found"));

            Item itemEntity = itemOptional.get();
            itemEntity.setQty((itemEntity.getQty() + itemDTO.getQty()));

            itemRepo.save(itemEntity);

        } catch (Exception e) {
            log.error("Method updateStock : ", e);
            throw e;
        }
    }

    @Override
    public void updateItemImage(ItemDTO itemDTO) {
        try {
            log.info("Execute method updateItemImage params {}", itemDTO.getId());

            //check item available or not
            Optional<Item> itemOptional = itemRepo.findById(itemDTO.getId());
            itemOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Item details not found"));

            Item itemEntity = itemOptional.get();
            itemEntity.setImage(fileUploader.saveBase64File(itemDTO.getImage()));

            itemRepo.save(itemEntity);

        } catch (Exception e) {
            log.error("Method updateItemImage : ", e);
            throw e;
        }
    }
}
