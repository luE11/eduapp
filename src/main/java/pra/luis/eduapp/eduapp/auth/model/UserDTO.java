package pra.luis.eduapp.eduapp.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

public class UserDTO {
    @NotNull(message = "Username field is required")
    protected String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password field is required")
    protected String password;
    @NotNull(message = "At least one role must be supplied")
    protected int[] roles;

    public UserDTO() {
    }

    public UserDTO(String username, String password, int[] roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User toUser(){
        return new User(this.username, this.password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
