package pra.luis.eduapp.eduapp.courses.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import pra.luis.eduapp.eduapp.persons.model.Person;

/**
 * @author luE11 on 28/06/23
 */
@Entity
@NoArgsConstructor
@Table(name = "courses_has_students")
@IdClass(CourseStudentId.class)
public class CourseHasStudent {
    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @Id @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person student;
    @Column(name = "final_grade")
    private Double finalGrade;
    @Column(name = "is_subscribed", nullable = false)
    private Boolean isSubscribed = true;

    public CourseHasStudent(Course course, Person student) {
        this.course = course;
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }
    public Person getStudent() {
        return student;
    }
    public Double getFinalGrade() {
        return finalGrade;
    }
    public void setFinalGrade(Double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }
}