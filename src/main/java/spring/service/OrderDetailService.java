package spring.service;

import spring.dto.OrderDetailDTO;

import java.util.List;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
public interface OrderDetailService {

    List<OrderDetailDTO> getAllOrderDetails();

    void saveOrder(List<OrderDetailDTO> orderDetailDTOList);

}
