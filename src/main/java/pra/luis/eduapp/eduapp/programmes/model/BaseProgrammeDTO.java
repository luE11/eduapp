package pra.luis.eduapp.eduapp.programmes.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pra.luis.eduapp.eduapp.utils.validator.FileType;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseProgrammeDTO {
    @NotNull(message = "Programme name field is required")
    protected String name;

    public BaseProgrammeDTO(String name){ this.name = name; }

    public Programme toProgramme() {
        return new Programme(this.name);
    }
}
