package pra.luis.eduapp.eduapp.programmes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FullProgrammeDTO extends BaseProgrammeDTO {

    private String logoUrl;
    private int studentsQuantity;
    private int subjectsQuantity;

    public FullProgrammeDTO(Programme programme){
        this.name = programme.getName();
        this.logoUrl = programme.logoUrl;
        this.studentsQuantity = programme.getPersons().size();
        this.subjectsQuantity = 0; // TODO: add subjects quantity
    }

}
