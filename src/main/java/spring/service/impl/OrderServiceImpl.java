package spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.ItemDTO;
import spring.dto.UserCreateDTO;
import spring.dto.json.reponse.OrderDetailsResponseDTO;
import spring.dto.json.request.ChangeOrderStatusRequestDTO;
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

import java.util.ArrayList;
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
    private final ModelMapper modelMapper;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, ItemRepository itemRepository,
                            OrderDetailRepository orderDetailRepository, ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
        try {
            log.info("Execute method placeOrder params {}", placeOrderRequestDTO);

            Optional<User> userOptional = userRepository.findById(placeOrderRequestDTO.getCustomerId());
            userOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Customer details not found"));

            Orders orders = new Orders();
            orders.setUser(userOptional.get());
            orders.setDateOfPlaced(new Date());
            orders.setOrderStatus(OrderStatus.PENDING);

            Orders savedOrder = orderRepository.save(orders);

            List<ItemDTO> items = placeOrderRequestDTO.getItems();
            if (items == null || items.isEmpty())
                throw new CustomException(RESOURCE_NOT_FOUND, "Order items not found");

            for (ItemDTO requestedItem : items) {
                OrderDetail orderDetail = new OrderDetail();

                Optional<Item> itemOptional = itemRepository.findById(requestedItem.getId());
                itemOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Item details not found"));

                Item stockItem = itemOptional.get();
                if (requestedItem.getQty() > stockItem.getQty())
                    throw new CustomException(RESOURCE_NOT_FOUND, "Can't order too much item quantity than stock");

                orderDetail.setItem(stockItem);
                orderDetail.setOrders(savedOrder);
                orderDetail.setQty(requestedItem.getQty());

                orderDetailRepository.save(orderDetail);

                stockItem.setQty((stockItem.getQty() - requestedItem.getQty()));
                itemRepository.save(stockItem);

            }

        } catch (Exception e) {
            log.error("Method placeOrder : ", e);
            throw e;
        }
    }

    @Override
    public List<OrderDetailsResponseDTO> filterOrderDetails(int customerId, String status) {
        try {
            log.info("Execute method filterOrderDetails params customer id {} status {}", customerId, status);

            List<OrderDetailsResponseDTO> orderDetailsResponseDTOS = new ArrayList<>();

            List<Orders> ordersList = orderRepository.filterOrders(customerId, status);

            if (ordersList != null && !ordersList.isEmpty()) {

                for (Orders orders : ordersList) {

                    OrderDetailsResponseDTO orderDetailsResponseDTO = new OrderDetailsResponseDTO();
                    double totalPrice = 0.00;

                    orderDetailsResponseDTO.setId(orders.getId());
                    orderDetailsResponseDTO.setCustomer(modelMapper.map(orders.getUser(), UserCreateDTO.class));
                    orderDetailsResponseDTO.getCustomer().setPassword(null);
                    orderDetailsResponseDTO.setDateOfCompleted(orders.getDateOfCompleted());
                    orderDetailsResponseDTO.setDateOfPlaced(orders.getDateOfPlaced());
                    orderDetailsResponseDTO.setOrderStatus(orders.getOrderStatus());

                    List<OrderDetail> orderDetailList = orders.getOrderDetailList();
                    List<ItemDTO> orderItems = new ArrayList<>();

                    for (OrderDetail orderDetail : orderDetailList) {

                        Item item = orderDetail.getItem();

                        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
                        itemDTO.setQty(orderDetail.getQty());
                        itemDTO.setSubTotalPrice((orderDetail.getQty() * item.getPrice()));

                        totalPrice += itemDTO.getSubTotalPrice();

                        orderItems.add(itemDTO);

                    }

                    orderDetailsResponseDTO.setItems(orderItems);
                    orderDetailsResponseDTO.setTotalOrderPrice(totalPrice);

                    orderDetailsResponseDTOS.add(orderDetailsResponseDTO);
                }
            }

            return orderDetailsResponseDTOS;

        } catch (Exception e) {
            log.error("Method filterOrderDetails : ", e);
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void changeOrderStatus(ChangeOrderStatusRequestDTO requestDTO) {
        try {
            log.info("Execute method changeOrderStatus params {} ", requestDTO);

            Optional<Orders> ordersOptional = orderRepository.findById(requestDTO.getId());
            ordersOptional.orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND, "Order details not found"));

            Orders orders = ordersOptional.get();
            orders.setOrderStatus(requestDTO.getOrderStatus());

            orderRepository.save(orders);
        } catch (Exception e) {
            log.error("Method changeOrderStatus : ", e);
            throw e;
        }
    }
}
