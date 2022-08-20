package spring.dto.json.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.enums.OrderStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ChangeOrderStatusRequestDTO {

        private long id;
        private OrderStatus orderStatus;
}
