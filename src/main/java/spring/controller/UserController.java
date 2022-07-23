/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserCreateDTO;
import spring.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE} )
    public int createUser(@RequestBody UserCreateDTO dto){
        int userId = userService.createUser(dto);
        return  userId;
    }

    @GetMapping(path = "/login")
    public int loginUser(String tel,String password){
        int userId = userService.loginUser(tel, password);
        return userId;
    }

}
