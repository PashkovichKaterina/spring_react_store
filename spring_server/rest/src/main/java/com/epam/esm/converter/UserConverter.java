package com.epam.esm.converter;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public User toUser(UserDto userDto) {
        User user = null;
        if (userDto != null) {
            user = new User();
            user.setId(userDto.getId());
            user.setLogin(userDto.getLogin());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
        }
        return user;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = null;
        if (user != null) {
            userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setLogin(user.getLogin());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
        }
        return userDto;
    }

    public List<UserDto> toUserDtoList(List<User> user) {
        return user.stream().map(this::toUserDto).collect(Collectors.toList());
    }
}