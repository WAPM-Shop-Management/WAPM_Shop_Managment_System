/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.dto.UserCreateDTO;
import spring.dto.json.reponse.SignInResponseDTO;
import spring.dto.json.request.SignInRequestDTO;
import spring.entity.User;
import spring.exception.Oauth2CustomException;
import spring.repository.UserRepository;
import spring.service.UserService;
import spring.util.JWTManager;
import spring.util.MobileNumberValidator;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static com.google.common.hash.Hashing.sha256;

@Service
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final MobileNumberValidator mobileNumberValidator;
    private final JWTManager jwtManager;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, MobileNumberValidator mobileNumberValidator,
                           JWTManager jwtManager) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.mobileNumberValidator = mobileNumberValidator;
        this.jwtManager = jwtManager;
    }

    @Override
    public SignInResponseDTO createUser(UserCreateDTO dto) {
        try{
            log.info("Execute method createUser");

            // convert mobile number to specific format
            String mobileNumber = mobileNumberValidator.convertMobileNumber(dto.getTel());
            dto.setTel(mobileNumber);
            // encode password from simple way
            String password = sha256().hashString(dto.getPassword(), StandardCharsets.UTF_8).toString();
            dto.setPassword(password);

            //check user available or not
            User user = userRepository.findByTel(dto.getTel());
            if (user != null)
                dto.setId(user.getId());

            user = mapper.map(dto, User.class);

            // if new user set id from this
            user = userRepository.save(user);
            dto.setId(user.getId());

            dto.setPassword(null);

            String signJWS = jwtManager.signJWS(mobileNumber, password);
            return new SignInResponseDTO(signJWS, dto);
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

    @Override
    public SignInResponseDTO signInUser(SignInRequestDTO requestDTO) {
        try {
            log.info("Execute method signInUser");

            String mobileNumber = mobileNumberValidator.convertMobileNumber(requestDTO.getUsername());

            User user = userRepository.findByTel(mobileNumber);
            if(user == null)
                throw new Oauth2CustomException(401, "Incorrect mobile number");

            String password = sha256().hashString(requestDTO.getPassword(), StandardCharsets.UTF_8).toString();
            if(!user.getPassword().equals(password))
                throw new Oauth2CustomException(401, "Incorrect password");

            String signJWS = jwtManager.signJWS(mobileNumber, password);

            UserCreateDTO userCreateDTO = mapper.map(user, UserCreateDTO.class);
            userCreateDTO.setPassword(null);

            SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
            signInResponseDTO.setAccessToken(signJWS);
            signInResponseDTO.setUser(userCreateDTO);

            return signInResponseDTO;
        } catch (Exception e) {
            log.error("Method signInUser : ", e);
            throw e;
        }
    }
}
