package Films;


import java.time.LocalDate;

class FilmNotFoundException extends RuntimeException {

  FilmNotFoundException(Long id) {
    super("Could not find film " + id);
  }

  FilmNotFoundException(String title) {
    super("Could not find film " + title);
  }

  FilmNotFoundException(LocalDate date) {
    super("Could not find film " + date);
  }
}