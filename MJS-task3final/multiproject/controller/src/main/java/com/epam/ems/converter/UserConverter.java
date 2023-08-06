package com.epam.ems.converter;

import com.epam.ems.dto.UserDTO;
import com.epam.esm.entity.User;

public class UserConverter {

    private UserConverter() {
    }

    public static UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setStatus(user.getStatus());
        return userDTO;
    }

    public static User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());
        return user;
    }
}


