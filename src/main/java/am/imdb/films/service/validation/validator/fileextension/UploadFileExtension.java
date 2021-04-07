package am.imdb.films.service.validation.validator.fileextension;

import am.imdb.films.service.validation.model.FileExtension;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UploadFileExtensionValidator.class})
public @interface UploadFileExtension {

    String message() default "Invalid file format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    FileExtension[] extensions() default {};
}
