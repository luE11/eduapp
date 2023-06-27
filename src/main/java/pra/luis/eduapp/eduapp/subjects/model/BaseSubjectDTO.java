package pra.luis.eduapp.eduapp.subjects.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author luE11 on 22/06/23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseSubjectDTO {
    @NotNull(message = "subject name field is required")
    protected String subjectName;
    @NotNull(message = "credits field is required")
    protected Integer credits;
    protected Boolean subscribable = false;

}
