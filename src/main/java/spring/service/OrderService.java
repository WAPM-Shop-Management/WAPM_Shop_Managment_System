package spring.service;

import spring.dto.json.request.PlaceOrderRequestDTO;

public interface OrderService {

    void placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO);
}
