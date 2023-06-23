package pra.luis.eduapp.eduapp.subjects.model;

import lombok.Getter;
import lombok.Setter;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

/**
 * DTO class for consult single subject response
 * @author luE11 on 22/06/23
 */
@Getter
@Setter
public class FullSubjectDTO extends BaseSubjectDTO {

    private Programme programme;
    private Subject requiredSubject;
    // Listado cursos asociados

    public FullSubjectDTO(Subject subject) {
        super(subject.getSubject_name(), subject.getCredits(), subject.isSubscribable());
        this.programme = subject.getProgramme();
        this.requiredSubject = subject.getRequiredSubject();
    }
}
