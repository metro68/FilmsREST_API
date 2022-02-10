package com.example.Films;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import Films.FilmApplication;
import Films.FilmController;

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
    FilmController filmController;

    @Test
    public void allTest() throws Exception {

        mvc.perform(get("/api/films/allFilms")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[*].id").isNotEmpty()); // [*] checks all ids. Entire line ensures all ids are
                                                              // present as expected
    }

    @Test
    public void allTitlesTest() throws Exception {

        mvc.perform(get("/api/films/titles")
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].actor").doesNotExist())
                .andExpect(jsonPath("$[0].actress").doesNotExist())
                .andExpect(jsonPath("$[0].awards").doesNotExist())
                .andExpect(jsonPath("$[0].director").doesNotExist())
                .andExpect(jsonPath("$[0].length").doesNotExist())
                .andExpect(jsonPath("$[0].popularity").doesNotExist())
                .andExpect(jsonPath("$[0].subject").doesNotExist())
                .andExpect(jsonPath("$[0].year").doesNotExist());

    }

    @Test
    public void oneTest() throws Exception {

        mvc.perform(get("/api/films/{id}", 2)
                .contentType(APPLICATION_JSON))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void allActorsTest() throws Exception {

        mvc.perform(get("/api/films/actors")
                .contentType(APPLICATION_JSON))
                // .andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].actor").isNotEmpty())
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
                .andExpect(jsonPath("$[*].director").isNotEmpty())
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