package Films;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
interface FilmRepository extends JpaRepository<Film, Long> {


    List<Film> findAllByDirector(String director);

    List<Film> findAllByYear(Date year);
    
}
