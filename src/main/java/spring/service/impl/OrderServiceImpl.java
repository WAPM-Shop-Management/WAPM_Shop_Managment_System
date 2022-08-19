package spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.ItemDTO;
import spring.dto.json.request.PlaceOrderRequestDTO;
import spring.entity.Item;
import spring.entity.OrderDetail;
import spring.entity.Orders;
import spring.entity.User;
import spring.enums.OrderStatus;
import spring.exception.CustomException;
import spring.repository.ItemRepository;
import spring.repository.OrderDetailRepository;
import spring.repository.OrderRepository;
import spring.repository.UserRepository;
import spring.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static spring.constant.ApplicationConstant.RESOURCE_NOT_FOUND;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, ItemRepository itemRepository, OrderDetailRepository orderDetailRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderDetailRepository = orderDetailRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
        try{
            log.info("Execute method placeOrder params {}", placeOrderRequestDTO);

            Optional<User> userOptional = userRepository.findById(placeOrderRequestDTO.getCustomerId());
            userOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Customer details not found"));

            Orders orders = new Orders();
            orders.setUser(userOptional.get());
            orders.setDateOfPlaced(new Date());
            orders.setOrderStatus(OrderStatus.PENDING);

            Orders savedOrder = orderRepository.save(orders);

            List<ItemDTO> items = placeOrderRequestDTO.getItems();
            if(items == null || items.isEmpty())
                throw new CustomException(RESOURCE_NOT_FOUND, "Order items not found");

            for (ItemDTO requestedItem : items) {
                OrderDetail orderDetail = new OrderDetail();

                Optional<Item> itemOptional = itemRepository.findById(requestedItem.getId());
                itemOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Item details not found"));

                Item stockItem = itemOptional.get();
                if(requestedItem.getQty() > stockItem.getQty())
                    throw new CustomException(RESOURCE_NOT_FOUND, "Can't order too much item quantity than stock");

                orderDetail.setItem(stockItem);
                orderDetail.setOrders(savedOrder);
                orderDetail.setQty(requestedItem.getQty());

                orderDetailRepository.save(orderDetail);

                stockItem.setQty((stockItem.getQty() - requestedItem.getQty()));
                itemRepository.save(stockItem);

            }

        }catch (Exception e){
            log.error("Method placeOrder : ", e);
            throw e;
        }
    }
}
