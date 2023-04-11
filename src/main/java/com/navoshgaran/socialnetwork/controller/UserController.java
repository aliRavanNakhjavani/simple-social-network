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

    @PostMapping("/send-request")
    public void sendRequest(@RequestBody RequestDto sendRequestDto){
        userService.sendRequest(sendRequestDto.getClientUsername(), sendRequestDto.getTargetUsername());
    }

    @GetMapping("/see-sended-requests/{clientUsername}")
    public List<GetUserDto> seeSendedRequests(@PathVariable String clientUsername){
        return userService.seeSendedRequests(clientUsername);
    }

    @GetMapping("/see-received-requests/{clientUsername}")
    public List<GetUserDto> seeReceivedRequests(@PathVariable String clientUsername){
        return userService.seeReceivedRequests(clientUsername);
    }

    @PostMapping("/confirm-request")
    public void confirmRequest(@RequestBody RequestDto requestDto){
        userService.confirmRequest(requestDto.getClientUsername(), requestDto.getTargetUsername());
    }

    @PostMapping("/reject-request")
    public void rejectRequest(@RequestBody RequestDto requestDto){
        userService.rejectRequest(requestDto.getClientUsername(), requestDto.getTargetUsername());
    }
}
