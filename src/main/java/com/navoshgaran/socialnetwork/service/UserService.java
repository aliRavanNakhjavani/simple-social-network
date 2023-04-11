package com.navoshgaran.socialnetwork.service;

import com.navoshgaran.socialnetwork.entity.User;
import com.navoshgaran.socialnetwork.exception.DuplicateException;
import com.navoshgaran.socialnetwork.exception.OperationException;
import com.navoshgaran.socialnetwork.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}