package spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.json.request.ChangeOrderStatusRequestDTO;
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
        return new ResponseEntity<>(new StandardResponse(200, "The Order has been successfully received.", null), HttpStatus.OK);
    }

    /**
     * Filter Order Details
     *
     * @param status     status of order which need to filter order
     * @param customerId specific customer id
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> filterOrderDetails(@RequestParam(value = "status", required = false) String status,
                                                               @RequestParam(value = "customerId", required = false) String customerId) {

        int filterCustomerId = customerId == null ? 0 : Integer.parseInt(customerId);
        String filterStatus = null;
        if (status != null && !status.equals("ALL")) filterStatus = status;

        return new ResponseEntity<>(new StandardResponse(200, "The Order details have been successfully fetched.",
                orderService.filterOrderDetails(filterCustomerId, filterStatus)), HttpStatus.OK);
    }

    /**
     * Change Order Status
     *
     * @param requestDTO request body
     */
    @PatchMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> changeOrderStatus(@PathVariable long orderId,
                                                              @RequestBody ChangeOrderStatusRequestDTO requestDTO) {

        requestDTO.setId(orderId);
        orderService.changeOrderStatus(requestDTO);
        return new ResponseEntity<>(new StandardResponse(200, "The Order status has been successfully updated.", null), HttpStatus.OK);
    }

}
