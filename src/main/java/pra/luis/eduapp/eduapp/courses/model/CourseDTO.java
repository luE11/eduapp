package pra.luis.eduapp.eduapp.courses.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author luE11 on 4/07/23
 */
@Getter
@Setter
public class CourseDTO extends BaseCourseDTO {

    @JsonProperty(value = "subjectId")
    @NotNull(message = "Subject id is required")
    private int subjectId;
    @JsonProperty(value = "teacherId")
    @NotNull(message = "Teacher id is required")
    private int teacherId;

    public CourseDTO(String idCode, String schedule, String state, int maxCapacity,
                     int subjectId, int teacherId) {
        super(idCode, schedule, state, maxCapacity);
        this.subjectId = subjectId;
        this.teacherId = teacherId;
    }
}
