package pra.luis.eduapp.eduapp.programmes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import pra.luis.eduapp.eduapp.persons.model.Person;

import java.util.Set;

@Entity
@Table(name = "programmes")
public class Programme {
    @Id
    @Column(name = "programme_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected int id;
    protected String name;

    @JsonIgnore
    @OneToMany(mappedBy="programme", targetEntity = Person.class)
    protected Set<Person> persons;

    public Programme() {
    }
}
