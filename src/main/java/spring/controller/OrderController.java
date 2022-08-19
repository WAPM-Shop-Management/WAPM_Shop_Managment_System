package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.json.request.PlaceOrderRequestDTO;
import spring.service.OrderService;
import spring.util.StandardResponse;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Place New Order
     *
     * @param requestDTO request body
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> placeNewOrder(@RequestBody PlaceOrderRequestDTO requestDTO) {

        orderService.placeOrder(requestDTO);
        return new ResponseEntity<>(new StandardResponse(200, "Success", null), HttpStatus.OK);
    }
}
