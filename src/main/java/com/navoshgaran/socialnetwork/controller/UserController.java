package com.navoshgaran.socialnetwork.controller;

import com.navoshgaran.socialnetwork.dto.LoginUserDto;
import com.navoshgaran.socialnetwork.dto.RegisterUserDto;
import com.navoshgaran.socialnetwork.entity.User;
import com.navoshgaran.socialnetwork.mapper.UserMapper;
import com.navoshgaran.socialnetwork.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
