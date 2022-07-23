package spring.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.OrderDetailDTO;
import spring.entity.OrderDetail;
import spring.repo.OrderDetailRepo;
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

    private final OrderDetailRepo orderDetailRepo;
    private final ModelMapper mapper;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepo orderDetailRepo, ModelMapper mapper) {
        this.orderDetailRepo = orderDetailRepo;
        this.mapper = mapper;
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        List<OrderDetail> all = orderDetailRepo.findAll();
        return mapper.map(all, new TypeToken<List<OrderDetailDTO>>() {
        }.getType());
    }

    @Override
    public void saveOrder(List<OrderDetailDTO> orderDetailDTOList) {
        if(!orderDetailDTOList.isEmpty()){
            orderDetailDTOList.forEach(orderDetailData ->{
                orderDetailData.setOdDate(new Date());
                orderDetailData.setOdStatus("Pending");
                OrderDetail orderDetail = orderDetailRepo.findById(orderDetailData.getId());
                if(orderDetail == null){
                    OrderDetail orderDetail2 = mapper.map(orderDetailData, OrderDetail.class);
                    orderDetailRepo.save(orderDetail2);
                }
            });
        }
    }
}

