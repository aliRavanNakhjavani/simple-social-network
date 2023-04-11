package com.navoshgaran.socialnetwork.service;

import com.navoshgaran.socialnetwork.dto.GetUserDto;
import com.navoshgaran.socialnetwork.entity.User;
import com.navoshgaran.socialnetwork.exception.*;
import com.navoshgaran.socialnetwork.mapper.UserMapper;
import com.navoshgaran.socialnetwork.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveOrUpdate(User user){
        try {
            userRepo.save(user);
        } catch(Exception e) {
            logger.error("save or update of User : " + user.getUsername() + " Was not successful. ");
            logger.error(e.getMessage());
        throw new OperationException("Unexpected error");
        }
    }

    public User getUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(
                () -> new NotFoundException("There Is No User"));
    }



    public void registerUser(User user){

        if(userRepo.existsByEmail(user.getEmail())){
            throw new DuplicateException("Email Already Exists!");
        }
        if (userRepo.existsByUsername(user.getUsername())){
            throw new DuplicateException("Username Already Exists!");
        }

        user.setRegisterDate(LocalDateTime.now());
        //TODO ENCODE PASSWORD
        saveOrUpdate(user);
    }


    public boolean isValidLogin(User user){
        if (user.getLoginDate() != null){
            return user.getLoginDate().isAfter(LocalDateTime.now());
        } else {
            return false;
        }
    }

    public boolean isValidLoginByUsername(String username){
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new NotFoundException("There Is No User"));
        return isValidLogin(user);
    }

    public void loginUser(User user) {
        User completedUser = userRepo.findByUsername(user.getUsername()).orElseThrow(
                () -> new NotFoundException("There Is No User With username : " + user.getUsername()));
        if (!completedUser.getPassword().equals(user.getPassword())){
            throw new InvalidInputException("Wrong Password");
        }
        if (isValidLogin(completedUser)){
            return;
        }
        completedUser.setLoginDate(LocalDateTime.now().plusDays(5));
        saveOrUpdate(completedUser);
    }

    public List<GetUserDto> searchByUsername(String searchString){
        List<User> users = userRepo.findByUsernameContainingIgnoreCase(searchString);
        List<GetUserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.INSTANCE.UserToGetUserDto(user));
        }
        return userDtos;
    }

    public void sendRequest(String clientUsername, String targetUsername) {
        if (!isValidLoginByUsername(clientUsername)){
            throw new ExpiredLoginException("Login First!");
        }
        User clientUser = getUserByUsername(clientUsername);
        User targetUser = getUserByUsername(targetUsername);
        if (clientUser.getRequestReceiver().contains(targetUser)) {
            throw new DuplicateException("Request Already Sended!");
        }
        clientUser.sendRequest(targetUser);
        saveOrUpdate(clientUser);
        saveOrUpdate(targetUser);
    }
}
