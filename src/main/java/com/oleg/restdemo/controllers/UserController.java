package com.oleg.restdemo.controllers;

import com.oleg.restdemo.exceptions.ResponseResultException;
import com.oleg.restdemo.exceptions.UserAlreadyExistsException;
import com.oleg.restdemo.models.ApplicationUser;
import com.oleg.restdemo.repos.UserRepository;
import com.oleg.restdemo.services.morningMessage.MorningMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private MorningMessageService mailService;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MorningMessageService mailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @GetMapping
    public List<ApplicationUser> getAllUsers(@AuthenticationPrincipal String username) {
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody ApplicationUser user) {
        Optional<ApplicationUser> userFromDB = userRepository.findByName(user.getName());
        if(userFromDB.isPresent()) throw new UserAlreadyExistsException(user.getName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        throw new ResponseResultException("ok");
    }
}
