package pra.luis.eduapp.eduapp.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    private String[] typesAllowed;

    @Override
    public void initialize(FileType requiredIfChecked) {
        this.typesAllowed = requiredIfChecked.typesAllowed();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String filename = file.getOriginalFilename();
        if(filename!=null){
            String extension = filename.substring(filename.lastIndexOf(".") + 1);
            return Arrays.asList(typesAllowed).contains(extension.toLowerCase());
        }
        return false;
    }
}
