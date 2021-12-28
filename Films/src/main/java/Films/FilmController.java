package Films;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/films")
class FilmController {

    private FilmRepository repository;

    // Constructor injection used here rather than @autowired injection, constructor
    // is recommended by spring
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

    // Returns all titles and IDs
    @GetMapping("/titles")
    public List<Map<String, Object>> allTitles() {

        List<Film> films = repository.findAll();
        List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> idTitlePair = new LinkedHashMap<String, Object>();
            idTitlePair.put("id", film.getId());
            idTitlePair.put("title", film.getTitle());
            response.add(idTitlePair);
        }
        return response;
    }

    // Get films by ID - exception won't show for 'jq pretty print' - not a bug
    @GetMapping("/{id}")
    Film one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    // Return all actors names
    @GetMapping("/actors")
    public Set<Map<String, Object>> allActors() {

        List<Film> films = repository.findAll();
        ArrayList<Map<String, Object>> answer = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> actorName = new HashMap<String, Object>();
            actorName.put("Actor", film.getActor());

            // Some entries have no actor listed
            if (actorName.get("Actress") != "") {
                answer.add(actorName);
            }
        }

        Collections.sort(answer, new FilmSortbyValue("Actor"));

        // To remove duplicates
        Set<Map<String, Object>> response = new LinkedHashSet<Map<String, Object>>(answer);

        return response;
    }

    // Return all actresses' names
    @GetMapping("/actresses")
    public Set<Map<String, Object>> allActresses() {

        List<Film> films = repository.findAll();
        ArrayList<Map<String, Object>> answer = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> actressName = new HashMap<String, Object>();
            actressName.put("Actress", film.getActress());

            // Some entries have no actress listed
            if (actressName.get("Actress") != "") {
                answer.add(actressName);
            }
        }
        Collections.sort(answer, new FilmSortbyValue("Actress"));

        // To remove duplicates
        Set<Map<String, Object>> response = new LinkedHashSet<Map<String, Object>>(answer);
        return response;
    }

    // Return all directors names
    @GetMapping("/directors")
    public Set<Map<String, Object>> allDirectors() {

        List<Film> films = repository.findAll();
        ArrayList<Map<String, Object>> answer = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> directorName = new HashMap<String, Object>();
            directorName.put("Director", film.getDirector());

            // Some entries have no director listed
            if (directorName.get("Actress") != "") {
                answer.add(directorName);
            }
        }

        Collections.sort(answer, new FilmSortbyValue("Director"));

        // To remove duplicates
        Set<Map<String, Object>> response = new LinkedHashSet<Map<String, Object>>(answer);
        return response;
    }

    // Get film titles by date. Date format (YYY-MM-DD) handled in
    // application.properties file
    @GetMapping("/date/{year}")
    public ArrayList<Map<String, Object>> getFilmsByDate(@PathVariable LocalDate year) {

        List<Film> films = repository.findAllByYear(year);
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
            filmTitle.put("Title", film.getTitle());
            response.add(filmTitle);
        }
        Collections.sort(response, new FilmSortbyValue("Title"));

        // To remove duplicates - unnecessary, should be handled in put mapping
        // Set<Map<String, Object>> response = new LinkedHashSet<Map<String,
        // Object>>(answer);
        return response;
    }

    // Get film titles by director name using a query with multiple string
    // parameters. API call format - curl -v
    // localhost:8080/api/film/"director?firstName=Pedro&secondName=Almodovar"
    @GetMapping("/director")
    public ArrayList<Map<String, Object>> getFilmsByDirector(@RequestParam String firstName,
            @RequestParam String secondName) {

        // ',' added below to match the format in csv file
        String director = firstName + ", " + secondName;
        List<Film> films = repository.findAllByDirector(director);
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> directorFilm = new LinkedHashMap<String, Object>();
            directorFilm.put("Title", film.getTitle());
            response.add(directorFilm);
        }

        Collections.sort(response, new FilmSortbyValue("Title"));
        return response;
    }

    // Get film titles by actor name using a query with multiple string parameters.
    // API call format - curl -v
    // localhost:8080/api/film/"actor?firstName=Pedro&secondName=Almodovar"
    @GetMapping("/actor")
    public ArrayList<Map<String, Object>> getFilmsByActor(@RequestParam String firstName,
            @RequestParam String secondName) {

        // ',' added below to match the format in csv file
        String actor = firstName + ", " + secondName;
        List<Film> films = repository.findAllByActor(actor);
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> actorFilm = new LinkedHashMap<String, Object>();
            actorFilm.put("Title", film.getTitle());
            response.add(actorFilm);
        }

        Collections.sort(response, new FilmSortbyValue("Title"));
        return response;
    }

    // Get film titles by actress name using a query with multiple string
    // parameters. API call format - curl -v
    // localhost:8080/api/film/"actress?firstName=Pedro&secondName=Almodovar"
    @GetMapping("/actress")
    public ArrayList<Map<String, Object>> getFilmsByActress(@RequestParam String firstName,
            @RequestParam String secondName) {

        // ',' added below to match the format in csv file
        String actress = firstName + ", " + secondName;
        List<Film> films = repository.findAllByDirector(actress);
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        for (Film film : films) {
            Map<String, Object> actressFilm = new LinkedHashMap<String, Object>();
            actressFilm.put("Title", film.getTitle());
            response.add(actressFilm);
        }

        Collections.sort(response, new FilmSortbyValue("Title"));
        return response;
    }

    // Returns movies in a particular range of lengths. API call format - curl -v
    // localhost:8080/api/film/"length?lt=90&gt=45"
    @GetMapping("/length")
    public ArrayList<Map<String, Object>> getFilmsByLength(@RequestParam String lt, @RequestParam String gt) {

        List<Film> films = repository.findAll();
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        for (Film film : films) {

            // LinkedHashMap used tp ensure title appears before length
            Map<String, Object> filmOfLength = new LinkedHashMap<String, Object>();
            if (film.getLength() > Integer.valueOf(gt) && film.getLength() < Integer.valueOf(lt)) {
                filmOfLength.put("Title", film.getTitle());
                filmOfLength.put("Length", film.getLength());
                response.add(filmOfLength);
            }
        }

        Collections.sort(response, new FilmSortbyValue("Length"));
        return response;
    }

    // Returns movies based on if it was an 70s, 80s, 90s, etc movie.
    // format - curl -v localhost:8080/api/film/decade?suffix=90s"
    @GetMapping("/decade")
    public ArrayList<Map<String, Object>> getFilmsByDecade(@RequestParam String suffix) {

        List<Film> films = repository.findAll();
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();

        int lowerBound = 1900 + Integer.valueOf(suffix.substring(0, 2));

        // int lowerBound = yearToCompare;
        int upperBound = lowerBound + 10;

        for (Film film : films) {

            // LinkedHashMap used tp ensure title appears before length
            Map<String, Object> filmInDecade = new LinkedHashMap<String, Object>();
            if ((int) film.getYear().getYear() < upperBound && (int) film.getYear().getYear() >= lowerBound) {
                filmInDecade.put("Year", film.getYear());
                filmInDecade.put("Title", film.getTitle());
                response.add(filmInDecade);
            }
        }

        Collections.sort(response, new FilmSortbyValue("Year"));
        return response;
    }

    // Returns movies based on if it was an 18th, 19th, 20th, 21st century, etc
    // movie.
    // format - curl -v localhost:8080/api/film/century?suffix=20th"
    @GetMapping("/century")
    public ArrayList<Map<String, Object>> getFilmsByCentury(@RequestParam String suffix) {

        List<Film> films = repository.findAll();
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();

        // 21st century started in (21-1)*100 = 2000
        int lowerBound = (Integer.valueOf(suffix.substring(0, 2)) - 1) * 100;

        // int lowerBound = yearToCompare;
        int upperBound = lowerBound + 100;

        for (Film film : films) {

            // LinkedHashMap used tp ensure title appears before length
            Map<String, Object> filmInDecade = new LinkedHashMap<String, Object>();
            if ((int) film.getYear().getYear() < upperBound && (int) film.getYear().getYear() >= lowerBound) {
                filmInDecade.put("Year", film.getYear());
                filmInDecade.put("Title", film.getTitle());
                response.add(filmInDecade);
            }
        }

        Collections.sort(response, new FilmSortbyValue("Year"));
        return response;
    }

    //Add new film to record
    /*@PostMapping(path = "/newFilm", consumes = "application/json")
    public Film newFilm(@RequestBody Film newFilm) {
        return repository.save(newFilm);
    }*/

    /*@PostMapping(path = "/newFilm", consumes = "application/x-www-form-urlencoded")
    Film newFilm(Film newFilm) {
        return repository.save(newFilm);
    }*/

    /*
     * // Update an existing record
     * 
     * @PutMapping("{id}")
     * Film replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {
     * return repository.findById(id)
     * .map(film -> {
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