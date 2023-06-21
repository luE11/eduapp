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
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "programme_id", referencedColumnName = "programme_id", nullable = false)
    protected Programme programme;

}
