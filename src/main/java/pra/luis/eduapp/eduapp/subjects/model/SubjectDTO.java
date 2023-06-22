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

    protected int programmeId;
    protected Integer requiredSubjectId;

    public SubjectDTO(String subjectName, int credits, boolean canSubscribe,
                      int programmeId, Integer requiredSubjectId){
        super(subjectName, credits, canSubscribe);
        this.programmeId = programmeId;
        this.requiredSubjectId = requiredSubjectId;
    }

    public Subject toSubject(){
        return new Subject(this.subjectName, this.credits, this.canSubscribe);
    }
}
