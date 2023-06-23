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
@ToString(of = { "id", "subject_name" })
@Table(name = "subjects")
public class Subject {
    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected int id;
    protected String subject_name;
    protected int credits;
    /** False by default. Indicates if a subject course can be taken by students */
    protected boolean subscribable;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "programme_id", referencedColumnName = "programme_id", nullable = false)
    protected Programme programme;
    /**
     * Cyclical relationship. A subject must be took by a student before the student can take another
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "requiredsubject_id", referencedColumnName = "subject_id", nullable = false)
    protected Subject requiredSubject;

    public Subject(String subject_name, int credits, boolean subscribable) {
        this.subject_name = subject_name;
        this.credits = credits;
        this.subscribable = subscribable;
    }

    public Subject(String subject_name, int credits, boolean subscribable,
                   Programme programme, Subject requiredSubject) {
        this.subject_name = subject_name;
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
        this.subject_name = subject.subject_name;
        this.credits = subject.credits;
    }
}
