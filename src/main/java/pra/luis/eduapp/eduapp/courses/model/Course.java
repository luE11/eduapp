package pra.luis.eduapp.eduapp.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.luis.eduapp.eduapp.persons.model.Person;
import pra.luis.eduapp.eduapp.subjects.model.Subject;

import java.util.Set;

/**
 * @author luE11 on 28/06/23
 */
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    @Column(name = "id_code")
    private String idCode;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CourseState state = CourseState.CREATED;
    @Column(name = "max_capacity")
    private int maxCapacity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "person_id", nullable = false)
    private Person teacher;
    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CourseHasStudent> students;

    // https://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example

    public Course(String idCode, String schedule, CourseState state, int maxCapacity) {
        this.idCode = idCode;
        this.schedule = schedule;
        this.state = state;
        this.maxCapacity = maxCapacity;
    }

    public void updateBaseProperties(CourseDTO courseDTO){
        this.idCode = courseDTO.getIdCode();
        this.schedule = courseDTO.getSchedule();
        this.state = CourseState.valueOf(courseDTO.getState());
        this.maxCapacity = courseDTO.getMaxCapacity();
    }

    public int getId() {
        return id;
    }

    public String getIdCode() {
        return idCode;
    }

    public String getSchedule() {
        return schedule;
    }

    public CourseState getState() {
        return state;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Subject getSubject() {
        return subject;
    }

    public Person getTeacher() {
        return teacher;
    }
    public Set<CourseHasStudent> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "idCode='" + idCode + '\'' +
                ", state=" + state +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
