package Films;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class FilmModelAssembler implements RepresentationModelAssembler<Film, EntityModel<Film>> {

  @Override
  public EntityModel<Film> toModel(Film film) {

    return EntityModel.of(film, //
        linkTo(methodOn(FilmController.class).one(film.getId())).withSelfRel(),
        linkTo(methodOn(FilmController.class).all()).withRel("Films"));
  }
}