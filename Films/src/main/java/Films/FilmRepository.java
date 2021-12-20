package Films;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface FilmRepository extends JpaRepository<Film, Long> {


    List<Film> findAllByDirector(String director);

    List<Film> findAllByYear(LocalDate year);
    
}