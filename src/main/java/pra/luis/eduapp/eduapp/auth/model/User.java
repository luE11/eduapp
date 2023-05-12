package pra.luis.eduapp.eduapp.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pra.luis.eduapp.eduapp.persons.model.Person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected int id;
    protected String username;
    protected String password;

    @ManyToMany
    @JoinTable(
            name = "users_has_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    protected Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    protected Person person;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(User user){
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.roles = user.roles;
        this.person = user.person;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, person);
    }
}
