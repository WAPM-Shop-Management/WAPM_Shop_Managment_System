package spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.json.request.SignInRequestDTO;
import spring.service.UserService;
import spring.util.StandardResponse;

@RestController
@RequestMapping("/api/v1/oauth")
@CrossOrigin
public class OauthController {

    private final UserService userService;

    public OauthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> signInUser(@RequestBody SignInRequestDTO requestDTO) {
        return new ResponseEntity<>(new StandardResponse(200, "The User has been successfully logged into the system.", userService.signInUser(requestDTO)),
                HttpStatus.OK);
    }
}
