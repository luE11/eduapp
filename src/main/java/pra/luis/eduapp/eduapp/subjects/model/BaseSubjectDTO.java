package pra.luis.eduapp.eduapp.subjects.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author luE11 on 22/06/23
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class BaseSubjectDTO {
    @NotNull(message = "subject name field is required")
    protected String subjectName;
    @NotNull(message = "credits field is required")
    protected int credits;
    protected boolean canSubscribe = false;

}
