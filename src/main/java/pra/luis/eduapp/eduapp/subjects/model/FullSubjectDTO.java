package pra.luis.eduapp.eduapp.subjects.model;

import jakarta.validation.constraints.NotNull;

/**
 * DTO class for consult single subject response
 * @author luE11 on 22/06/23
 */
public class FullSubjectDTO extends BaseSubjectDTO {

    /*
    programa, asignatura requerida, cursos?
     */

    public FullSubjectDTO(@NotNull(message = "subject name field is required") String subjectName, @NotNull(message = "credits field is required") int credits, boolean canSubscribe) {
        super(subjectName, credits, canSubscribe);
    }
}
