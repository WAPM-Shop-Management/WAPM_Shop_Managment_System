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
import spring.repo.UserRepo;
import spring.service.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public int createUser(UserCreateDTO dto) {
        int userId =0;
        User user = userRepo.findByNicAndTelAndEmail(dto.getNic(),dto.getTelNo(),dto.getEmail());

        if(user == null){
            User userMap = mapper.map(dto, User.class);
            User saveUser = userRepo.save(userMap);
            userId =saveUser.getId();
        }
        return userId;
    }
}
