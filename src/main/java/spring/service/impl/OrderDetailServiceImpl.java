package spring.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.OrderDetailDTO;
import spring.dto.OrderDetailListDTO;
import spring.entity.OrderDetail;
import spring.repository.OrderDetailRepository;
import spring.service.OrderDetailService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, ModelMapper mapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> all = orderDetailRepository.findAll();
        return mapper.map(all, new TypeToken<List<OrderDetailDTO>>() {
        }.getType());
    }

    @Override
    public void saveOrder(OrderDetailListDTO orderDetailListDTO) {
        if(!orderDetailListDTO.getOrderDetailDTOList().isEmpty()){
            orderDetailListDTO.getOrderDetailDTOList().forEach(orderDetailData ->{

                orderDetailData.setOdPlaceAt(new Date());
                orderDetailData.setOdStatus("Pending");
                OrderDetail orderDetail = orderDetailRepository.findById(orderDetailData.getId());
                if(orderDetail == null){
                    OrderDetail orderDetail2 = mapper.map(orderDetailData, OrderDetail.class);
                    orderDetailRepository.save(orderDetail2);
                }
            });
        }
    }
}

