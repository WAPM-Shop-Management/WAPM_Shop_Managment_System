/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dto.UserCreateDTO;
import spring.entity.User;
import spring.repository.UserRepository;
import spring.service.UserService;
import spring.util.MobileNumberValidator;

import javax.transaction.Transactional;

@Service
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final MobileNumberValidator mobileNumberValidator;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, MobileNumberValidator mobileNumberValidator) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.mobileNumberValidator = mobileNumberValidator;
    }

    @Override
    public int createUser(UserCreateDTO dto) {
        try{
            log.info("Execute method createUser");

            int userId = 0;

            // convert mobile number to specific format
            dto.setTelNo(mobileNumberValidator.convertMobileNumber(dto.getTelNo()));

            //check user available or not
            User user = userRepository.findByTel(dto.getTelNo());

            if (user == null) {
                User userMap = mapper.map(dto, User.class);
                User saveUser = userRepository.save(userMap);
                userId = saveUser.getId();
            }

            return userId;
        }catch (Exception e){
            log.error("Method createUser : ", e);
            throw e;
        }
    }

    @Override
    public int loginUser(String tel, String password) {
        User user = userRepository.findByTel(tel);
        int userId = 0;

        if (user != null) {
            if (user.getPassword().equalsIgnoreCase(password)) {
                userId = user.getId();
            } else {
                userId = 0;
            }
        }
        return userId;
    }
}
