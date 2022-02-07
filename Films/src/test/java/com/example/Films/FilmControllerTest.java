package com.example.Films;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import Films.Film;
import Films.FilmController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { FilmController.class })
@ExtendWith(SpringExtension.class)
// @SpringBootTest
@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilmController filmController;

    public String firstName = "Test";
    public String secondName = "TestSecond";

    @Test
    public void allTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        List<Film> allFilms = singletonList(film);

        given(filmController.all()).willReturn(allFilms);

        mvc.perform(get("/api/films/allFilms")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].actor", is(film.getActor())))
                .andExpect(jsonPath("$[0].actress", is(film.getActress())))
                .andExpect(jsonPath("$[0].awards", is(film.getAwards())))
                .andExpect(jsonPath("$[0].director", is(film.getDirector())))
                .andExpect(jsonPath("$[0].length", is(film.getLength())))
                .andExpect(jsonPath("$[0].popularity", is(film.getPopularity())))
                .andExpect(jsonPath("$[0].subject", is(film.getSubject())))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].id", is(film.getId().intValue())))
                .andExpect(jsonPath("$[0].year", is(film.getYear().toString())));
    }

    @Test
    public void allTitlesTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        // An array list used to match controller design where a smaller map
        // 'idTitlePair' is added to a bigger map
        List<Map<String, Object>> allTitlesOfFilms = new ArrayList<Map<String, Object>>();
        Map<String, Object> idTitlePair = new LinkedHashMap<String, Object>();
        idTitlePair.put("id", film.getId());
        idTitlePair.put("title", film.getTitle());
        allTitlesOfFilms.add(idTitlePair);

        given(filmController.allTitles()).willReturn(allTitlesOfFilms);

        mvc.perform(get("/api/films/titles")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].size()", is(2))) // Expects a size two rather than one because map has id and
                                                           // title key value pair as separate things
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].id", is(film.getId().intValue())));
        // .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    public void oneTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        given(filmController.one(film.getId())).willReturn(film);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/films/{id}", 2000L)
                // mvc.perform(MockMvcRequestBuilders.get("/api/films/{id}",film.getId())
                .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].id", is(film.getId().intValue())))
                .andExpect(jsonPath("$[0].actor", is(film.getActor())))
                .andExpect(jsonPath("$[0].actress", is(film.getActress())))
                .andExpect(jsonPath("$[0].awards", is(film.getAwards())))
                .andExpect(jsonPath("$[0].director", is(film.getDirector())))
                .andExpect(jsonPath("$[0].length", is(film.getLength())))
                .andExpect(jsonPath("$[0].popularity", is(film.getPopularity())))
                .andExpect(jsonPath("$[0].subject", is(film.getSubject())))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].id", is(film.getId().intValue())))
                .andExpect(jsonPath("$[0].year", is(film.getYear().toString())));
    }

    @Test
    public void allActorsTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        // An array list used to match controller design where a smaller map
        // 'idTitlePair' is added to a bigger map
        Set<Map<String, Object>> allTitles = new LinkedHashSet<Map<String, Object>>();
        Map<String, Object> actorName = new HashMap<String, Object>();
        actorName.put("actor", film.getActor());
        allTitles.add(actorName);

        given(filmController.allActors()).willReturn(allTitles);

        mvc.perform(get("/api/films/actors")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Checks size of JSON output
                .andExpect(jsonPath("$[0].size()", is(1))) // Checks size of 'allTitles'
                .andExpect(jsonPath("$[0].actor", is(film.getActor())));
        // .andDo(MockMvcResultHandlers.print()); // Prints JSON output to Debugger
    }

    @Test
    public void allActressesTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        // An array list used to match controller design where a smaller map
        // 'idTitlePair' is added to a bigger map
        Set<Map<String, Object>> allTitles = new LinkedHashSet<Map<String, Object>>();
        Map<String, Object> actressName = new HashMap<String, Object>();
        actressName.put("actress", film.getActress());
        allTitles.add(actressName);

        given(filmController.allActresses()).willReturn(allTitles);

        mvc.perform(get("/api/films/actresses")
                .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Checks size of JSON output
                .andExpect(jsonPath("$[0].size()", is(1))) // Checks size of 'allTitles'
                .andExpect(jsonPath("$[0].actress", is(film.getActress())));
        // .andDo(MockMvcResultHandlers.print()); // Prints JSON output to Debugger
    }

    @Test
    public void allDirectorsTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        // An array list used to match controller design where a smaller map
        // 'idTitlePair' is added to a bigger map
        Set<Map<String, Object>> allTitles = new LinkedHashSet<Map<String, Object>>();
        Map<String, Object> directorName = new HashMap<String, Object>();
        directorName.put("director", film.getDirector());
        allTitles.add(directorName);

        given(filmController.allDirectors()).willReturn(allTitles);

        mvc.perform(get("/api/films/directors")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Checks size of JSON output
                .andExpect(jsonPath("$[0].size()", is(1))) // Checks size of 'allTitles'
                .andExpect(jsonPath("$[0].director", is(film.getDirector())));
        // .andDo(MockMvcResultHandlers.print()); // Prints JSON output to Debugger
    }

    @Test
    public void getFilmsByDateTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        given(filmController.getFilmsByDate(film.getYear())).willReturn(response);

        mvc.perform(get("/api/films/{year}", LocalDate.parse("2022-05-01"))
                // mvc.perform(MockMvcRequestBuilders.get("/api/films/{id}",film.getId())
                .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year", is(film.getYear())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFilmsByDirectorTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("Test, Director");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        String first = film.getDirector().split(" ")[0].replaceAll(",", "");
        String second = film.getDirector().split(" ")[1];

        given(filmController.getFilmsByDirector(first, second)).willReturn(response);

        mvc.perform(get("/director")
                .contentType(APPLICATION_JSON)
                .param("firstName", first)
                .param("secondName", second))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].director", is(film.getDirector())));
    }

    @Test
    public void getFilmsByActorTest() throws Exception {
        Film film = new Film();
        film.setActor(firstName + ", " + secondName);
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        // Names will usually have a comma to split so remove the comma at the end of
        // the first name
        String first = film.getActor().split(" ")[0].replaceAll(",", "");
        String second = film.getActor().split(" ")[1];
        given(filmController.getFilmsByActor(first, second)).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/api/films/actor/")
                .contentType(APPLICATION_JSON)
                .param("firstName", firstName)
                .param("secondName", secondName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFilmsByActressTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress(firstName + ", " + secondName);
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(2);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        // Names will usually have a comma to split so remove the comma at the end of
        // the first name
        String first = film.getActress().split(" ")[0].replaceAll(",", "");
        String second = film.getActress().split(" ")[1];
        given(filmController.getFilmsByActress(first, second)).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/api/films/actress/")
                .contentType(APPLICATION_JSON)
                .param("firstName", firstName)
                .param("secondName", secondName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFilmsByLengthTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(20);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("2022-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        String lower = "19";
        String greater = "21";

        given(filmController.getFilmsByLength(lower, greater)).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/api/films/length/")
                .contentType(APPLICATION_JSON)
                .param("lt", lower)
                .param("gt", greater))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].length", is(film.getLength())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFilmsByDecadeTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(20);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("1922-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        String decade = "20s";

        given(filmController.getFilmsByDecade(decade)).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/api/films/decade/")
                .contentType(APPLICATION_JSON)
                .param("suffix", decade))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].year", is(film.getYear())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFilmsByCenturyTest() throws Exception {
        Film film = new Film();
        film.setActor("TestActor");
        film.setActress("TestActress");
        film.setAwards(false);
        film.setDirector("TestDirector");
        film.setLength(20);
        film.setId(2000L);
        film.setPopularity(100);
        film.setSubject("TestSubject");
        film.setTitle("TestTitle");
        film.setYear(LocalDate.parse("1922-05-01"));

        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        Map<String, Object> filmTitle = new LinkedHashMap<String, Object>();
        filmTitle.put("title", film.getTitle());
        response.add(filmTitle);

        String period = "20th";

        given(filmController.getFilmsByCentury(period)).willReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/api/films/century/")
                .contentType(APPLICATION_JSON)
                .param("suffix", period))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(film.getTitle())))
                .andExpect(jsonPath("$[0].year", is(film.getYear())))
                .andDo(MockMvcResultHandlers.print());
    }

    // @Test
    // public void deleteFilmTest() throws Exception {
    // mvc.perform(MockMvcRequestBuilders.delete("/api/films/{id}", 1L))
    // .andExpect(status().isAccepted());
    // }
}