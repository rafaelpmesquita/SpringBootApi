package cyber.login.jwt.system.loginsystemjwt.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cyber.login.jwt.system.loginsystemjwt.services.interfaces.IUserService;
import cyber.login.jwt.system.loginsystemjwt.dtos.UpdateUserDto;
import cyber.login.jwt.system.loginsystemjwt.models.UserModel;

@RestController
@RequestMapping("user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserModel> getMe(){
        return userService.getCurrentUser();
    }
    
    @PutMapping("")
    public ResponseEntity<UserModel> updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return userService.getAllUsers();
    }
    
    @DeleteMapping("")
    public ResponseEntity<Object> deleteUser(@RequestParam UUID id){
        return userService.deleteUser(id);
    }

}
