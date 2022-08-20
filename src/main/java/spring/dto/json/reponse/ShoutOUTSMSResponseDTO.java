package spring.dto.json.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ShoutOUTSMSResponseDTO {

    private String status;
    private String description;
    private int cost;
    private List<Object> responses;
}
