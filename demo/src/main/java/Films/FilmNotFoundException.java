package Films;


import java.util.Date;

class FilmNotFoundException extends RuntimeException {

  FilmNotFoundException(Long id) {
    super("Could not find film " + id);
  }

  FilmNotFoundException(String title) {
    super("Could not find film " + title);
  }

  FilmNotFoundException(Date date) {
    super("Could not find film " + date);
  }
}