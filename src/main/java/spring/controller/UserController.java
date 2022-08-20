/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dto.UserCreateDTO;
import spring.service.DashboardService;
import spring.service.UserService;
import spring.util.StandardResponse;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final DashboardService dashboardService;

    public UserController(UserService userService, DashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<StandardResponse> createUser(@RequestBody UserCreateDTO dto){
        return new ResponseEntity<>(new StandardResponse(200, "The user profile has been successfully created.", userService.createUser(dto)),
                HttpStatus.OK);
    }

    /**
     * Get Admin Dashboard Details
     */
    @GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> getDashboardDetails() {

        return new ResponseEntity<>(new StandardResponse(200, "The dashboard details have been successfully fetched.", dashboardService.getDashboardDetails()),
                HttpStatus.OK);
    }

}
