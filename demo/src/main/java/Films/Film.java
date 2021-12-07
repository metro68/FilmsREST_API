package Films;


import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
public class Film {

  private @Id @GeneratedValue Long id;

  @CsvBindByName
  @CsvDate("dd/MM/yyyy")
  private Date year;

  @CsvBindByName
  private int length;

  @CsvBindByName(capture="([^ ]+) .*")
  private String title;

  @CsvBindByName
  private String subject;

  @CsvBindByName
  private String actor;

  @CsvBindByName
  private String actress;

  @CsvBindByName(capture="([^ ]+) .*")
  private String director;

  @CsvBindByName
  private int popularity;

  @CsvBindByName
  private boolean awards;

  Film() {
  }

  Film(Date year, int length, String title, String subject, String actor, String actress, String director,
      int popularity, boolean awards) {

    this.year = year;
    this.length = length;
    this.title = title;
    this.subject = subject;
    this.actor = actor;
    this.actress = actress;
    this.director = director;
    this.popularity = popularity;
    this.awards = awards;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getYear() {
    return this.year;
  }

  public void setYear(Date year) {
    this.year = year;
  }

  public int getLength() {
    return this.length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getActor() {
    return this.actor;
  }

  public void setActor(String actor) {
    this.actor = actor;
  }

  public String getActress() {
    return this.actress;
  }

  public void setActress(String actress) {
    this.actress = actress;
  }

  public String getDirector() {
    return this.director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public int getPopularity() {
    return this.popularity;
  }

  public void setPopularity(int popularity) {
    this.popularity = popularity;
  }

  public boolean isAwards() {
    return this.awards;
  }

  public boolean getAwards() {
    return this.awards;
  }

  public void setAwards(boolean awards) {
    this.awards = awards;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Film)) {
      return false;
    }
    Film film = (Film) o;
    return Objects.equals(id, film.id) && Objects.equals(year, film.year) && length == film.length
        && Objects.equals(title, film.title) && Objects.equals(subject, film.subject)
        && Objects.equals(actor, film.actor) && Objects.equals(actress, film.actress)
        && Objects.equals(director, film.director) && popularity == film.popularity && awards == film.awards;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, year, length, title, subject, actor, actress, director, popularity, awards);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", year='" + getYear() + "'" +
        ", length='" + getLength() + "'" +
        ", title='" + getTitle() + "'" +
        ", subject='" + getSubject() + "'" +
        ", actor='" + getActor() + "'" +
        ", actress='" + getActress() + "'" +
        ", director='" + getDirector() + "'" +
        ", popularity='" + getPopularity() + "'" +
        ", awards='" + isAwards() + "'" +
        "}";
  }
}