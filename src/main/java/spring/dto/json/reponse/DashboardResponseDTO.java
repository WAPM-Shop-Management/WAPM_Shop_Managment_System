package spring.dto.json.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DashboardResponseDTO {

    private int pendingOrders;
    private int ongoingOrders;
    private int completeOrders;
    private int customerCount;
}
