package pra.luis.eduapp.eduapp.programmes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.subjects.model.Subject;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "programmes")
public class Programme {
    @Id
    @Column(name = "programme_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected int id;
    protected String name;
    protected String logoUrl;

    @JsonIgnore
    @OneToMany(mappedBy="programme", targetEntity = Person.class)
    protected Set<Person> persons;

    @JsonIgnore
    @OneToMany(mappedBy = "programme", targetEntity = Subject.class)
    protected Set<Subject> subjects;

    public Programme() {
    }

    public Programme(String name) {
        this.name = name;
    }

    public Programme(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public void updateProperties(Programme programme){
        this.name = programme.getName();
    }

    @Override
    public String toString() {
        return "Programme{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
