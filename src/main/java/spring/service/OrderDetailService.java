package spring.service;

import spring.dto.OrderDetailDTO;
import spring.dto.OrderDetailListDTO;

import java.util.List;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
public interface OrderDetailService {

    List<OrderDetailDTO> getAllOrderDetails();

    void saveOrder(OrderDetailListDTO orderDetailListDTO);

}
