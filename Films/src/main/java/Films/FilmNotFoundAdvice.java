package Films;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class FilmNotFoundAdvice {

  @ResponseBody // signals that the advice is rendered straight into the response body
  @ExceptionHandler(FilmNotFoundException.class) // Advice only responds if 'filmNotFound' is thrown
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String filmNotFoundHandler(FilmNotFoundException ex) {
    return ex.getMessage();
  }
}