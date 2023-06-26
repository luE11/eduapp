package pra.luis.eduapp.eduapp.subjects.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author luE11 on 22/06/23
 */
@Getter
@Setter
public class SubjectDTO extends BaseSubjectDTO {

    @NotNull(message = "ProgrammeId field is required")
    protected int programmeId;
    protected Integer requiredSubjectId;

    public SubjectDTO(String subjectName, int credits, boolean subscribable,
                      int programmeId, Integer requiredSubjectId){
        super(subjectName, credits, subscribable);
        this.programmeId = programmeId;
        this.requiredSubjectId = requiredSubjectId;
    }

    public Subject toSubject(){
        return new Subject(this.subjectName, this.credits, this.subscribable);
    }
}
