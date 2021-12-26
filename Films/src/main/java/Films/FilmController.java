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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/films")
class FilmController {

    private FilmRepository repository;
    //Constructor injection used here rather than @autowired injection, constructor is recommended by spring
    FilmController(FilmRepository repository) {

        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]

    // Returns all film records - Working as intended
    @GetMapping("/allFilms")
    List<Film> all() {
        return repository.findAll();
    }

    // Returns all titles and IDs. Maps have unique keys, bear that in mind when implementing this! - Working as intended
    @GetMapping("/titles")
    public List<Map<String,Object>> allTitles() {

        List<Film> films = repository.findAll();
        List<Map<String,Object>> response = new ArrayList<Map<String,Object>>();
        for (Film film : films) {
            Map<String, Object> idTitlePair = new LinkedHashMap<String, Object>();
            idTitlePair.put("id", film.getId());
            idTitlePair.put("title", film.getTitle());
            response.add(idTitlePair);
        }
        return response;
    }

    // Get films by ID - exception not working as intended
    @GetMapping("/{id}")
    Film one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    // Return all actors names - working as intended without duplicates
    @GetMapping("/actors")
    public Set<Map<String,String>> allActors() {

        List<Film> films = repository.findAll();
        ArrayList<Map<String,String>> answer = new ArrayList<Map<String,String>>();
        for (Film film : films) {
            Map<String, String> actorName = new HashMap<String, String>();
            actorName.put("Actor", film.getActor());

            //Some entries have no actor listed
            if (actorName.get("Actress") != ""){
                answer.add(actorName);
            }
        }

        Collections.sort(answer, new FilmSortbyValue("Actor"));

        //To remove duplicates
        Set<Map<String,String>> response = new LinkedHashSet<Map<String,String>>(answer); 

        return response;
    }

    // Return all actresses' names - working as intended without duplicates
    @GetMapping("/actresses")
    public Set<Map<String,String>> allActresses() {

        List<Film> films = repository.findAll();
        ArrayList<Map<String,String>> answer = new ArrayList<Map<String,String>>();
        for (Film film : films) {
            Map<String, String> actressName = new HashMap<String, String>();
            actressName.put("Actress", film.getActress());

            //Some entries have no actress listed
            if (actressName.get("Actress") != ""){
                answer.add(actressName);
            }
        }
        Collections.sort(answer, new FilmSortbyValue("Actress"));
        
        //To remove duplicates
        Set<Map<String,String>> response = new LinkedHashSet<Map<String,String>>(answer); 
        return response;
    }

    // Return all directors names - working as intended without duplicates
    @GetMapping("/directors")
    public Set<Map<String,String>> allDirectors() {

        List<Film> films = repository.findAll();
        ArrayList<Map<String,String>> answer = new ArrayList<Map<String,String>>();
        for (Film film : films) {
            Map<String, String> directorName = new HashMap<String, String>();
            directorName.put("Director", film.getDirector());

            //Some entries have no director listed
            if (directorName.get("Actress") != ""){
                answer.add(directorName);
            }
        }

        Collections.sort(answer, new FilmSortbyValue("Director"));

        //To remove duplicates
        Set<Map<String,String>> response = new LinkedHashSet<Map<String,String>>(answer); 
        return response;
    }

    // Get film titles by date. Date format (YYY-MM-DD) handled in application.properties file - working as intended without duplicates
    @GetMapping("/date/{year}")
    public ArrayList<Map<String,String>> getFilmsByDate(@PathVariable LocalDate year) {

        List<Film> films = repository.findAllByYear(year);
        ArrayList<Map<String,String>> response = new ArrayList<Map<String,String>>();
        for (Film film : films) {
            Map<String, String> filmTitle = new LinkedHashMap<String, String>();
            filmTitle.put("Title", film.getTitle());
            response.add(filmTitle);
        }
        Collections.sort(response, new FilmSortbyValue("Title"));
        
        //To remove duplicates - unnecessary, should be handled in put mapping
        //Set<Map<String,String>> response = new LinkedHashSet<Map<String,String>>(answer);
        return response;
    }

    // Get film titles by director name using a query string with multi parameters - not working as intended
    @GetMapping("/director")
    public Map<String, Object> getFilmsByDirector(@RequestParam String firstName, @RequestParam String secondName) {
        
        String director = firstName + " " + secondName;
        List<Film> films = repository.findAllByDirector(director);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        for (Film film : films) {
            response.put("Title", film.getTitle());
        }
        return response;
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