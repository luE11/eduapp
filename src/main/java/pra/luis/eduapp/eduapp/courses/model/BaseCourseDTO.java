package pra.luis.eduapp.eduapp.courses.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pra.luis.eduapp.eduapp.utils.validator.EnumVal;

/**
 * @author luE11 on 4/07/23
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseCourseDTO {
    @JsonProperty(value = "idCode")
    private String idCode;
    @JsonProperty(value = "schedule")
    @NotNull(message = "Schedule is required")
    private String schedule;
    @JsonProperty(value = "state")
    @NotNull(message = "State is required")
    @EnumVal(message = "State is invalid. Must be 'CREATED', 'ACTIVE' or 'FINISHED'",
        enumClass = CourseState.class)
    private String state;
    @JsonProperty(value = "maxCapacity")
    @NotNull(message = "Max capacity is required")
    private int maxCapacity;


    public BaseCourseDTO(String idCode, String schedule, String state, int maxCapacity) {
        this.idCode = idCode;
        this.schedule = schedule;
        this.state = state!=null ? state.toUpperCase() : "CREATED";
        this.maxCapacity = maxCapacity;
    }

    public Course toCourse(){
        return new Course( this.idCode, this.schedule, CourseState.valueOf(this.state), this.maxCapacity);
    }
}
