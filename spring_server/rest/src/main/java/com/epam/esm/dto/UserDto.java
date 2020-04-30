package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String login;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public UserDto() {
    }

    public UserDto(Long id, String login, String email, String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) obj;
        return (id == null ? id == userDto.id : id.equals(userDto.id))
                && (login == null ? login == userDto.login : login.equals(userDto.login))
                && (email == null ? email == userDto.email : email.equals(userDto.email))
                && (password == null ? password == userDto.password : password.equals(userDto.password));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (login == null ? 0 : login.hashCode())
                + (email == null ? 0 : email.hashCode()) + (password == null ? 0 : password.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; LOGIN=" + login + "; EMAIL=" + email + "; PASSWORD=" + password;
    }
}
