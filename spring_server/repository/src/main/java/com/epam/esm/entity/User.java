package com.epam.esm.entity;

import javax.persistence.EnumType;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(50)")
    private String login;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(120)")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @PrePersist
    public void setDefaultUserRole() {
        role = UserRole.USER;
    }

    public User() {
    }

    public User(Long id, String login, String email, String password, UserRole role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public UserRole getRole() {
        return role;
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

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return (id == null ? id == user.id : id.equals(user.id))
                && (login == null ? login == user.login : login.equals(user.login))
                && (email == null ? email == user.email : email.equals(user.email))
                && (password == null ? password == user.password : password.equals(user.password))
                && (role == null ? role == user.role : role.equals(user.role));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (login == null ? 0 : login.hashCode())
                + (email == null ? 0 : email.hashCode()) + (password == null ? 0 : password.hashCode())
                + (role == null ? 0 : role.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; LOGIN=" + login + "; EMAIL=" + email
                + "; PASSWORD=" + password + "; ROLE=" + role;
    }
}
