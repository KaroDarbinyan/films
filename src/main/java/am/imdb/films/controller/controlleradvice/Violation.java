package am.imdb.films.controller.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Violation {

  private final String fieldName;

  private final String message;

}
