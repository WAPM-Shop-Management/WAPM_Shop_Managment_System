package spring.service;

import spring.dto.json.reponse.OrderDetailsResponseDTO;
import spring.dto.json.request.ChangeOrderStatusRequestDTO;
import spring.dto.json.request.PlaceOrderRequestDTO;

import java.util.List;

public interface OrderService {

    void placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO);

    List<OrderDetailsResponseDTO> filterOrderDetails(int customerId, String status);

    void changeOrderStatus(ChangeOrderStatusRequestDTO requestDTO);
}
