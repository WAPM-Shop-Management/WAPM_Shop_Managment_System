package spring.service;

import spring.dto.UserCreateDTO;
import spring.dto.json.reponse.SignInResponseDTO;
import spring.dto.json.request.SignInRequestDTO;

public interface UserService {

    SignInResponseDTO createUser(UserCreateDTO dto);

    int loginUser(String userName,String password);

    SignInResponseDTO signInUser(SignInRequestDTO requestDTO);
}
