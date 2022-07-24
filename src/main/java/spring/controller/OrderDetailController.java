package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.OrderDetailDTO;
import spring.dto.OrderDetailListDTO;
import spring.service.OrderDetailService;
import spring.util.StandardResponse;

import java.util.List;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */

@RestController
@RequestMapping("/api/v1/order-detail")
@CrossOrigin
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/view-all")
    public ResponseEntity<?> getAllOrders() {
        List<OrderDetailDTO> allOrderDetails = orderDetailService.getAllOrderDetails();
        return new ResponseEntity<>(new StandardResponse(200, "Success", allOrderDetails), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},path = "")
    public ResponseEntity<?> saveOrder(@RequestBody OrderDetailListDTO orderDetailListDTO){
        orderDetailService.saveOrder(orderDetailListDTO);
        return new ResponseEntity<>(new StandardResponse(200,"success",null),HttpStatus.OK);
    }
}
