package pra.luis.eduapp.eduapp.subjects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

/**
 * @author luE11
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = { "id", "subjectName" })
@Table(name = "subjects")
public class Subject {
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected int id;
    protected String subjectName;
    protected int credits;
    /** False by default. Indicates if a subject course can be taken by students */
    protected boolean subscribable;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "programme_id", referencedColumnName = "programme_id", nullable = false)
    protected Programme programme;
    /**
     * Cyclical relationship. A subject must be took by a student before the student can take another
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "requiredsubject_id", referencedColumnName = "subject_id")
    protected Subject requiredSubject;

    public Subject(String subjectName, int credits, boolean subscribable) {
        this.subjectName = subjectName;
        this.credits = credits;
        this.subscribable = subscribable;
    }

    public Subject(String subjectName, int credits, boolean subscribable,
                   Programme programme, Subject requiredSubject) {
        this.subjectName = subjectName;
        this.credits = credits;
        this.subscribable = subscribable;
        this.programme = programme;
        this.requiredSubject = requiredSubject;
    }

    /**
     * Updates base attributes of subject by overwritting from another subject object
     * @param subject Subject object from which attributes will be updated
     */
    public void updateProperties(Subject subject){
        this.subjectName = subject.subjectName;
        this.credits = subject.credits;
    }
}
