package Films;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<Film> findByTitle(String title);

    Optional<Film> findByDirector(String director);;

    List<Film> findAllByDirector(String director);

    List<Film> findAllByYear(Date year);
    
}
