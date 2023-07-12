package pra.luis.eduapp.eduapp.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;

/**
 * @author luE11 on 11/07/23
 */
@Entity
@Table(name = "student_related_courses")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
public class StudentRelatedCoursesView implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_code")
    private String idCode;
    @Column(name = "schedule")
    private String schedule;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CourseState state;
    @Column(name = "max_capacity")
    private int maxCapacity;
    @Column(name = "subject_name")
    private String subjectName;
    @Column(name = "email")
    private String teacherEmail;
    @Column(name = "final_grade")
    private Double finalGrade;
    @Column(name = "is_subscribed")
    private boolean isSubscribed;
    @JsonIgnore
    @Column(name = "person_id")
    private int studentId;

    @Override
    public String toString() {
        return "StudentRelatedCoursesView{" +
                "idCode='" + idCode + '\'' +
                ", state=" + state +
                ", maxCapacity=" + maxCapacity +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
