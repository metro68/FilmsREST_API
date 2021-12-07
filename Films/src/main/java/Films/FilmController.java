package Films;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/films")
class FilmController {

    private final FilmRepository repository;

    FilmController(FilmRepository repository) {

        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]

    // Returns all film records
    @GetMapping("/allFilms")
    List<Film> all() {
        return repository.findAll();
    }

    // Returns the all titles and IDs
    @GetMapping("/titles")
    public Map<String, Object> allTitles() {

        List<Film> films = repository.findAll();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("id: ", film.getId());
            response.put("title: ", film.getTitle());
        }
        return response;
    }

    // Get films by ID
    @GetMapping("/{id}")
    Film one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    // Return all actors names
    @GetMapping("actors")
    public Map<String, Object> allActors() {

        List<Film> films = repository.findAll();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("Actor: ", film.getActor());
        }
        return response;
    }

    // Return all actresses' names
    @GetMapping("actresses")
    public Map<String, Object> allActresses() {

        List<Film> films = repository.findAll();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("Actress: ", film.getActress());
        }
        return response;
    }

    // Return all directors names
    @GetMapping("directors")
    public Map<String, Object> allDirectors() {

        List<Film> films = repository.findAll();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("Director: ", film.getDirector());
        }
        return response;
    }

    // Get film titles by date
    @GetMapping("/date/{YYYY-MM-DD}")
    public Map<String, Object> getFilmsByDate(@PathVariable Date year) {

        List<Film> films = repository.findAllByYear(year);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("Title: ", film.getTitle());
        }
        return response
                .orElseThrow(() -> new FilmNotFoundException(year));
    }

    // Get film titles by director
    @GetMapping("/director?firstName={}&secondName={}")
    public Map<String, Object> getFilmsByDirector(@PathVariable String director) {

        List<Film> films = repository.findAllByDirector(director);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("Title: ", film.getTitle());
        }
        return response
                .orElseThrow(() -> new FilmNotFoundException(director));
    }

    /*
     * @PutMapping("/films/{id}")
     * Film replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {
     * 
     * return repository.findById(id)
     * film.setTitle(newFilm.getTitle());
     * film.setSubject(newFilm.getSubject());
     * film.setActor(newFilm.getActor());
     * film.setActress(newFilm.getActress());
     * film.setDirector(newFilm.getDirector());
     * film.setPopularity(newFilm.getPopularity());
     * film.setAwards(newFilm.getAwards());
     * return repository.save(film);
     * })
     * .orElseGet(() -> {
     * newFilm.setId(id);
     * return repository.save(newFilm);
     * });
     * }
     */

    @DeleteMapping("/films/{id}")
    void deleteFilm(@PathVariable Long id) {
        repository.deleteById(id);
    }
}