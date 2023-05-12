package pra.luis.eduapp.eduapp.persons.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Base person DTO with basic Person model properties (abstract class)
 */

@Getter
@Setter
public abstract class BasePersonDTO {
    @JsonProperty(value = "firstName")
    @NotNull(message = "First name is required")
    protected String firstName;
    @JsonProperty(value = "lastName")
    @NotNull(message = "Last name is required")
    protected String lastName;
    @JsonProperty(value = "birthDate")
    @Past(message = "Birth date can't be future")
    protected Date birthDate;
    @JsonProperty(value = "email")
    @Email
    protected String email;
    @JsonProperty(value = "phoneNumber")
    protected String phoneNumber;
    @JsonProperty(value = "address")
    protected String address;

    public BasePersonDTO() { } // TODO: HttpMessageConversionException. Search update crud with dto example

    public BasePersonDTO(String firstName, String lastName, Date birthDate, String email, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Person toPersonObject(){
        return new Person(StringUtils.capitalize(firstName.toLowerCase()),
                StringUtils.capitalize(lastName.toLowerCase()),
                birthDate,
                email.toLowerCase(),
                phoneNumber,
                address);
    }

}
