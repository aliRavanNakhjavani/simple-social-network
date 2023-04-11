package com.navoshgaran.socialnetwork.controller;

import com.navoshgaran.socialnetwork.dto.GetUserDto;
import com.navoshgaran.socialnetwork.dto.LoginUserDto;
import com.navoshgaran.socialnetwork.dto.RegisterUserDto;
import com.navoshgaran.socialnetwork.dto.RequestDto;
import com.navoshgaran.socialnetwork.entity.User;
import com.navoshgaran.socialnetwork.mapper.UserMapper;
import com.navoshgaran.socialnetwork.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void userRegister(@Valid @RequestBody RegisterUserDto registerUserDto){
        User user = UserMapper.INSTANCE.registerUserDtoToUser(registerUserDto);
        userService.registerUser(user);
    }

    @PostMapping("/login")
    public void userLogin(@Valid @RequestBody LoginUserDto loginUserDto){
        User user = UserMapper.INSTANCE.loginUserDtoToUser(loginUserDto);
        userService.loginUser(user);
    }

    @GetMapping("/search-username")
    public List<GetUserDto> searchByUsername(@RequestParam String searchString){
        return userService.searchByUsername(searchString);
    }

//    @GetMapping("/search-username/{searchString}")
//    public List<GetUserDto> searchByUsername(@PathVariable String searchString){
//        return userService.searchByUsername(searchString);
//    }

    @PostMapping("/send-request")
    public void sendRequest(@RequestBody RequestDto sendRequestDto){
        userService.sendRequest(sendRequestDto.getClientUsername(), sendRequestDto.getTargetUsername());

    }
}
