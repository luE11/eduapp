package pra.luis.eduapp.eduapp.persons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.courses.model.Course;
import pra.luis.eduapp.eduapp.courses.model.CourseHasStudent;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected int id;
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    @Column(name = "birth_date")
    protected Date birthDate;
    protected String email;
    @Column(name = "phone_number")
    protected String phoneNumber;
    protected String address;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    protected User user;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="programme_id", nullable=false)
    protected Programme programme;
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseHasStudent> courses;
    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Course> directedCourses;

    public Person() {
    }

    public Person(String firstName, String lastName, User user, Programme programme) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
        this.programme = programme;
    }

    public Person(String firstName, String lastName, Date birthDate,
                  String email, String phoneNumber, String address, User user, Programme programme) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.user = user;
        this.programme = programme;
    }

    public Person(String firstName, String lastName, Date birthDate, String email, String phoneNumber,
                  String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void updateProperties(Person updatedPerson){
        this.firstName = updatedPerson.firstName;
        this.lastName = updatedPerson.lastName;
        this.birthDate = updatedPerson.birthDate;
        this.email = updatedPerson.email;
        this.phoneNumber = updatedPerson.phoneNumber;
        this.address = updatedPerson.address;
    }
    public Set<CourseHasStudent> getCourses() {
        return courses;
    }
    public Set<Course> getDirectedCourses() {
        return directedCourses;
    }
}
