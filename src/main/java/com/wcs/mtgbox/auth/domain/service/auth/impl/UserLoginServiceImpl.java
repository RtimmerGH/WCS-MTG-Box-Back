package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.UserLoginService;
import com.wcs.mtgbox.auth.infrastructure.exception.login.LoginErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public UserLoginServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Override
    public User login(User user) {
        User userEntity = getUserEntityByUsername(user.getUsername());
        if (!verifyHashedPasswordDuringLogin(user.getPassword(), userEntity.getPassword())){
            throw new LoginErrorException("Wrong username or password");
        }
        user.setRole(userEntity.getRole());
        return user;
    }

    @Override
    public boolean verifyHashedPasswordDuringLogin(String password, String hashedPassword) {
        return bcryptPasswordEncoder.matches(password, hashedPassword);
    }

    @Override
    public User getUserEntityByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}