package spring.service;

import spring.dto.UserCreateDTO;

public interface UserService {
    int createUser(UserCreateDTO dto);

    int loginUser(String userName,String password);
}
