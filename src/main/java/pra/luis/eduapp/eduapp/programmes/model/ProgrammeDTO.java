package pra.luis.eduapp.eduapp.programmes.model;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pra.luis.eduapp.eduapp.utils.validator.FileType;

@Getter
@Setter
public class ProgrammeDTO extends BaseProgrammeDTO {

    @FileType(typesAllowed = { "jpg", "png" }, message = "File doesn't match jpg or png extensions")
    protected MultipartFile logoImage;

}
