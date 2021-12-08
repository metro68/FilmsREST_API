package Films;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class FilmDatabase {

    private static FilmRepository repository;

    public static void main(String[] args) throws IOException {

        String fileName = "demo/src/main/resources/example_data1.csv";
        Path myPath = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Film> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Film.class);

            CsvToBean<Film> csvToBean = new CsvToBeanBuilder<Film>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Film> films = csvToBean.parse();

            for (Film film: films){
                repository.save(new Film(film));
            }

            //films.forEach(System.out::println);
        }
        catch (IOException e) {
            System.out.println("An error occurred in uploading the csv file.");
            e.printStackTrace();
        }
    }
}