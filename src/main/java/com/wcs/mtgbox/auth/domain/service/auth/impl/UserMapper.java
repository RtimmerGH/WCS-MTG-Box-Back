package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.wcs.mtgbox.auth.domain.dto.RoleEnum;
import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserMapper {

    public User transformUserRegistrationDtoInUserEntity(UserRegistrationDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setDepartment(userDTO.getDepartment());
        user.setCity(userDTO.getCity());
        user.setLastConnectionDate(LocalDateTime.now());
        user.setRole(new Role(1L, RoleEnum.USER));
        user.setIsActive(true);
        user.setIsBanned(false);
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public UserDTO transformUserEntityInUserDto(Optional<User> user) {
        if (user.isEmpty()) {
            throw new UserNotFoundErrorException();
        }
        UserDTO userDTO = new UserDTO();
        user.ifPresent(userToReturn -> {
           userDTO.setId(userToReturn.getId());
           userDTO.setEmail(userToReturn.getEmail());
           userDTO.setUsername(userToReturn.getUsername());
           userDTO.setIsActive(userToReturn.getIsActive());
           userDTO.setIsBanned(userToReturn.getIsBanned());
           userDTO.setDepartment(userToReturn.getDepartment());
           userDTO.setCity(userToReturn.getCity());
           userDTO.setLastConnectionDate(userToReturn.getLastConnectionDate());
           userDTO.setCreationDate(userToReturn.getCreationDate());
           userDTO.setRole(userToReturn.getRole());
           userDTO.setAvatar(userToReturn.getMedia());
        });
        return userDTO;
        }
}
