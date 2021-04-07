package am.imdb.films.service.validation.validator.fileextension;

import am.imdb.films.service.validation.model.FileExtension;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UploadFileExtensionValidator implements ConstraintValidator<UploadFileExtension, MultipartFile> {

    private UploadFileExtension constraint;


    @Override
    public void initialize(UploadFileExtension constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        Set<String> extensions = Arrays.stream(this.constraint.extensions()).map(FileExtension::name)
                .map(String::toLowerCase).collect(Collectors.toSet());

        if (extensions.contains(extension)) {
            return true;
        }

        String errorMessage = String.format("The file must be in %s format", String.join(", ", extensions));
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addConstraintViolation();

        return false;
    }

}
