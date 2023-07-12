package pra.luis.eduapp.eduapp.courses.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author luE11 on 11/07/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CourseFinalGradeDTO {
    @NotNull(message = "courseId is required")
    private int courseId;
    @NotNull(message = "personId is required")
    private int personId;
    @NotNull(message = "finalGrade is required")
    @PositiveOrZero(message = "finalGrade must be greater than zero or equals")
    private double finalGrade;
}
