package spring.dto.json.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.dto.ItemDTO;
import spring.dto.UserCreateDTO;
import spring.enums.OrderStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDetailsResponseDTO {

    private long id;
    private Date dateOfPlaced;
    private Date dateOfCompleted;
    private double totalOrderPrice;
    private OrderStatus orderStatus;
    private UserCreateDTO customer;
    private List<ItemDTO> items = new ArrayList<>();
}
