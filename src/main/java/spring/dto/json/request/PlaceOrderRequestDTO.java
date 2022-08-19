package spring.dto.json.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.dto.ItemDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PlaceOrderRequestDTO {

    private int customerId;
    private List<ItemDTO> items;
}
