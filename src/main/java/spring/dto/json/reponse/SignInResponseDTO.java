package spring.dto.json.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.dto.UserCreateDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInResponseDTO {

    private String accessToken;
    private UserCreateDTO user;

}
