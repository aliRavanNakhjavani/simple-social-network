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
import java.util.Set;

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
        if (clientUser.getFollowing().contains(targetUser)){
            throw new DuplicateException("You Are Following him Already");
        }
        clientUser.sendRequest(targetUser);
        saveOrUpdate(clientUser);
        saveOrUpdate(targetUser);
    }

    public List<GetUserDto> seeSendedRequests(String clientUsername) {
        if (!isValidLoginByUsername(clientUsername)){
            throw new ExpiredLoginException("Login First!");
        }
        Set<User> requestReceiver = getUserByUsername(clientUsername).getRequestReceiver();
        List<GetUserDto> sendedRequests = new ArrayList<>();
        for (User user: requestReceiver) {
            sendedRequests.add(UserMapper.INSTANCE.UserToGetUserDto(user));
        }
        return sendedRequests;
    }

    public List<GetUserDto> seeReceivedRequests(String clientUsername) {
        if (!isValidLoginByUsername(clientUsername)){
            throw new ExpiredLoginException("Login First!");
        }
        Set<User> requestSender = getUserByUsername(clientUsername).getRequestSender();
        List<GetUserDto> receivedRequests = new ArrayList<>();
        for (User user: requestSender) {
            receivedRequests.add(UserMapper.INSTANCE.UserToGetUserDto(user));
        }
        return receivedRequests;
    }

    public void confirmRequest(String clientUsername, String targetUsername) {
        if (!isValidLoginByUsername(clientUsername)){
            throw new ExpiredLoginException("Login First!");
        }
        User clientUser = getUserByUsername(clientUsername);
        User targetUser = getUserByUsername(targetUsername);
        if (!clientUser.getRequestSender().contains(targetUser)){
            throw new InvalidInputException("There is No Request to Confirm");
        }
        clientUser.confirmRequest(targetUser);
        saveOrUpdate(clientUser);
        saveOrUpdate(targetUser);
    }

    public void rejectRequest(String clientUsername, String targetUsername) {
        if (!isValidLoginByUsername(clientUsername)){
            throw new ExpiredLoginException("Login First!");
        }
        User clientUser = getUserByUsername(clientUsername);
        User targetUser = getUserByUsername(targetUsername);
        if (!clientUser.getRequestSender().contains(targetUser)){
            throw new InvalidInputException("There is No Request to reject");
        }
        clientUser.rejectRequest(targetUser);
        saveOrUpdate(clientUser);
        saveOrUpdate(targetUser);
    }
}
