package pra.luis.eduapp.eduapp.persons.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.apache.commons.lang3.StringUtils;
import pra.luis.eduapp.eduapp.auth.model.User;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

import java.util.Date;

public class PersonDTO {

    @NotNull(message = "At least one role must be supplied")
    protected int[] roles;
    @NotNull(message = "First name is required")
    protected String firstName;
    @NotNull(message = "Last name is required")
    protected String lastName;
    @Past(message = "Birth date can't be future")
    protected Date birthDate;
    @Email
    protected String email;
    protected String phoneNumber;
    protected String address;
    @NotNull(message = "Must specify a programme")
    protected int programmeId;

    public PersonDTO() {
    }

    public PersonDTO(int[] roles, String firstName, String lastName, int programmeId) {
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.programmeId = programmeId;
    }

    public PersonDTO(int[] roles, String firstName, String lastName, Date birthDate,
                     String email, String phoneNumber, String address, int programmeId) {
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.programmeId = programmeId;
    }

    public Person toPersonObject(){
        return new Person(StringUtils.capitalize(firstName.toLowerCase()),
                StringUtils.capitalize(lastName.toLowerCase()),
                birthDate,
                email,
                phoneNumber,
                address);
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(int programmeId) {
        this.programmeId = programmeId;
    }
}
