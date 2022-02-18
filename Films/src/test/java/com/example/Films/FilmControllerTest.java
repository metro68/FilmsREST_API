package com.example.Films;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import Films.FilmApplication;
import Films.FilmController;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = FilmApplication.class)

public class FilmControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    public FilmController filmController;

    @Test
    public void allTest() throws Exception {

        mvc.perform(get("/api/films/allFilms")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.filmList[*].id").isNotEmpty())// [*] checks all ids. Entire line ensures all ids are
                                                            // present as expected
                .andExpect(jsonPath("$._embedded.filmList[5].id").value(6))
                .andExpect(jsonPath("$._embedded.filmList[5].year").value("1983-01-01"))
                .andExpect(jsonPath("$._embedded.filmList[5].length").value(140))
                .andExpect(jsonPath("$._embedded.filmList[5].title").value("Octopussy"))
                .andExpect(jsonPath("$._embedded.filmList[5].subject").value("Action"))
                .andExpect(jsonPath("$._embedded.filmList[5].actor").value("Moore, Roger"))
                .andExpect(jsonPath("$._embedded.filmList[5].actress").value("Adams, Maud"))
                .andExpect(jsonPath("$._embedded.filmList[5].director").value("Glen, John"))
                .andExpect(jsonPath("$._embedded.filmList[5].popularity").value(68))
                .andExpect(jsonPath("$._embedded.filmList[5].awards").value(false))
                // Verify that there are 1659 titles in total
                .andExpect(jsonPath("$._embedded.filmList[*]", hasSize(1659)))
                // Picks a random film on the list and checks that all 10 objects are present
                .andExpect(jsonPath("$._embedded.filmList[100].size()", is(11)))
                // Tests for things that things that will never be on list
                .andExpect(jsonPath("$._embedded.filmList[?(@.id == 0)]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.year == '2029-30-30')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.length == 100000)]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.title == 'Andres De Fonollosa')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.subject == 'Crimewar')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.actor == 'Berlin Alonso')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.actress == 'Tokyo Corbero')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.director == 'Pina Denver')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.popularity == '101')]").doesNotExist())
                .andExpect(jsonPath("$._embedded.filmList[?(@.awards == 'generous')]").doesNotExist());
    }

    @Test
    public void allTitlesTest() throws Exception {

        mvc.perform(get("/api/films/titles")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[5].title").value("Octopussy"))
                // Verify that there are 1659 titles in total
                .andExpect(jsonPath("$[*]", hasSize(1659)))
                // Picks a random film on the list and checks that only title and id objects are
                // present
                .andExpect(jsonPath("$[100].size()", is(2)))
                // Tests for fields we don't expect to see
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist())
                // Tests for values of allowed fields we don't expect to see
                .andExpect(jsonPath("$[?(@.id == 100000)]").doesNotExist())
                .andExpect(jsonPath("$[?(@.title == 'Andres De Fonollosa')]").doesNotExist());

    }

    @Test
    public void oneTest() throws Exception {

        mvc.perform(get("/api/films/{id}", 3)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                // Picks a random film on the list and checks that all objects (10) + links(1)
                // requested in controller are present
                .andExpect(jsonPath("$.size()", is(11)))
                // Check all objects of this film are accurate
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.year").value("1983-01-01"))
                .andExpect(jsonPath("$.length").value(104))
                .andExpect(jsonPath("$.title").value("Dead Zone, The"))
                .andExpect(jsonPath("$.subject").value("Horror"))
                .andExpect(jsonPath("$.actor").value("Walken, Christopher"))
                .andExpect(jsonPath("$.actress").value("Adams, Brooke"))
                .andExpect(jsonPath("$.director").value("Cronenberg, David"))
                .andExpect(jsonPath("$.popularity").value(79))
                .andExpect(jsonPath("$.awards").value(false))
                // Check that another unrequested but existing film (13) isn't present
                .andExpect(jsonPath("$[?(@.id == 13)]").doesNotExist())
                .andExpect(jsonPath("$[?(@.year == '1966-01-01')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.length == 103)]").doesNotExist())
                .andExpect(jsonPath("$[?(@.title == 'A Man & a Woman')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.subject == 'Drama')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.actor == 'Trintignant, Jean-Louis')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.actress == 'Aimee, Anouk')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.director == 'Lelouch, Claude')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.popularity == 46)]").doesNotExist())
                .andExpect(jsonPath("$[?(@.awards == true)]").doesNotExist());
    }

    @Test
    public void allActorsTest() throws Exception {

        mvc.perform(get("/api/films/actors")
                .contentType(APPLICATION_JSON))
                // .andDo(print());
                .andExpect(status().isOk())
                // There are 759 unique actors in total
                .andExpect(jsonPath("$[*]", hasSize(759)))
                .andExpect(jsonPath("$[*].actor").isNotEmpty())
                // test for an actor that should be on the list
                .andExpect(jsonPath("$[?(@.actor == 'Willis, Bruce')]").exists())
                // test for a random actor who shouldn't be on the list
                .andExpect(jsonPath("$[?(@.actor == 'Andres De Fonollosa')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void allActressesTest() throws Exception {

        mvc.perform(get("/api/films/actresses")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].actress").isNotEmpty())
                // There are 1659 unique actresses in total
                .andExpect(jsonPath("$[*]", hasSize(710)))
                // test for an actress that should be on the list
                .andExpect(jsonPath("$[?(@.actress == 'Anderson, Jo')]").exists())
                // test for a random actress who shouldn't be on the list
                .andExpect(jsonPath("$[?(@.actress == 'Silene Oliveira')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void allDirectorsTest() throws Exception {
        mvc.perform(get("/api/films/directors")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                // There are 1659 unique directors in total
                .andExpect(jsonPath("$[*]", hasSize(755)))
                .andExpect(jsonPath("$[*].director").isNotEmpty())
                // test for a director that should be on the list
                .andExpect(jsonPath("$[?(@.director == 'Antonio, Lou')]").exists())
                // test for a random director who shouldn't be on the list
                .andExpect(jsonPath("$[?(@.director == 'El Professor')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void getFilmsByDateTest() throws Exception {

        mvc.perform(get("/api/films/date/{year}", "1978-01-01")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                // There are 18 films from the date 1978-01-01
                .andExpect(jsonPath("$[*]", hasSize(18)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'Wild Geese, The')]").exists())
                // test for a random title that shouldn't be on the list
                .andExpect(jsonPath("$[?(@.title == 'Monei Heeeeeest')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void getFilmsByDirectorTest() throws Exception {

        mvc.perform(get("/api/films/director")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Glen")
                .param("secondName", "John"))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                // There are 3 films with the director glen john
                .andExpect(jsonPath("$[*]", hasSize(3)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'For Your Eyes Only')]").exists())
                // test for a random title that shouldn't be on the list
                .andExpect(jsonPath("$[?(@.title == 'Monei Heeeeeest')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void getFilmsByActorTest() throws Exception {

        mvc.perform(get("/api/films/actor")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Connors")
                .param("secondName", "Chuck"))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                // There is 1 film with the actor connors chuck
                .andExpect(jsonPath("$[*]", hasSize(1)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'Target Eagle')]").exists())
                // test for a random title that shouldn't be on the list
                .andExpect(jsonPath("$[?(@.title == 'Monei Heeeeeest')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void getFilmsByActressTest() throws Exception {

        mvc.perform(get("/api/films/actress")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Adams")
                .param("secondName", "Brooke"))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                // There are 3 films with the actress adams brooke
                .andExpect(jsonPath("$[*]", hasSize(3)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'Cuba')]").exists())
                // test for a random title that exists but she didn't act in
                .andExpect(jsonPath("$[?(@.title == 'Tragedy of a Ridiculous Man')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
    }

    @Test
    public void getFilmsByLengthTest() throws Exception {

        mvc.perform(get("/api/films/length/")
                .contentType(APPLICATION_JSON)
                .param("lt", "105")
                .param("gt", "90"))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].length").isNotEmpty())
                // There are 496 films in the length range 90 - 105
                .andExpect(jsonPath("$[*]", hasSize(496)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'Critical Condition')]").exists())
                // test for a film with length 51 which shouldn't be on the list
                .andExpect(jsonPath("$[?(@.title == 'Outer Limits, The')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].year").doesNotExist());
        ;
    }

    @Test
    public void getFilmsByDecadeTest() throws Exception {

        mvc.perform(get("/api/films/decade/")
                .contentType(APPLICATION_JSON)
                .param("suffix", "70s"))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].year").isNotEmpty())
                // There are 18 films from the date 1978-01-01
                .andExpect(jsonPath("$[*]", hasSize(244)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'Getting Straight')]").exists())
                // test for a film that came out in the 80s that shouldn't be on the list
                .andExpect(jsonPath("$[?(@.title == 'Terminator, The')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist());
    }

    @Test
    public void getFilmsByCenturyTest() throws Exception {

        mvc.perform(get("/api/films/century/")
                .contentType(APPLICATION_JSON)
                .param("suffix", "20th"))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].year").isNotEmpty())
                // There are 18 films from the date 1978-01-01
                .andExpect(jsonPath("$[*]", hasSize(1659)))
                // test for a title that should be on the list
                .andExpect(jsonPath("$[?(@.title == 'Tango & Cash')]").exists())
                // test for a random title that shouldn't be on the list
                .andExpect(jsonPath("$[?(@.title == 'Monei Heeeeeest')]").doesNotExist())
                // Checking for fields that aren't supposed to be there
                .andExpect(jsonPath("$[*].director").doesNotExist())
                .andExpect(jsonPath("$[*].id").doesNotExist())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[*].awards").doesNotExist())
                .andExpect(jsonPath("$[*].actress").doesNotExist())
                .andExpect(jsonPath("$[*].popularity").doesNotExist())
                .andExpect(jsonPath("$[*].subject").doesNotExist())
                .andExpect(jsonPath("$[*].length").doesNotExist());
    }

    /*
     * @Test
     * public void newFilmTest() throws Exception {
     * mvc.perform(post("/api/films/newFilm"))
     * // .andDo(print())
     * .andExpect(status().isOk());
     * }
     * 
     * @Test
     * public void replaceFilmTest() throws Exception {
     * mvc.perform(put("/api/films/{id}", 3))
     * // .andDo(print())
     * .andExpect(status().isOk());
     * }
     */

    @Test
    public void deleteFilmTest() throws Exception {
        mvc.perform(delete("/api/films/{id}", 1))
                // .andDo(print())
                .andExpect(status().isOk());
    }
}