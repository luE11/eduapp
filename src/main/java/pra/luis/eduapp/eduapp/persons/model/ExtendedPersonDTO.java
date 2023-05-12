package pra.luis.eduapp.eduapp.persons.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Extended Person DTO including role ids and programme id
 */
@AllArgsConstructor
public class ExtendedPersonDTO extends BasePersonDTO {

    @NotNull(message = "At least one role must be supplied")
    protected int[] roles;
    @NotNull(message = "Must specify a programme")
    protected int programmeId;

    public ExtendedPersonDTO() {
    }

    public ExtendedPersonDTO(int[] roles, String firstName, String lastName, int programmeId) {
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.programmeId = programmeId;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    public int getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(int programmeId) {
        this.programmeId = programmeId;
    }
}
