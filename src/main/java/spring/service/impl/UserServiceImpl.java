/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.UserCreateDTO;
import spring.entity.User;
import spring.repository.UserRepository;
import spring.service.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public int createUser(UserCreateDTO dto) {
        int userId =0;
        User user = userRepository.findByTel(dto.getTelNo());

        if(user == null){
            User userMap = mapper.map(dto, User.class);
            User saveUser = userRepository.save(userMap);
            userId =saveUser.getId();
        }
        return userId;
    }

    @Override
    public int loginUser(String tel, String password) {
        User user = userRepository.findByTel(tel);
        int userId=0;

        if(user !=null){
            if(user.getPassword().equalsIgnoreCase(password)){
                userId = user.getId();
            }else {
                userId=0;
            }
        }
        return userId;
    }
}
