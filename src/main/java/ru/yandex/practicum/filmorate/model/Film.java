package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.model.helpres.Update;

import java.time.LocalDate;
import java.util.*;


@Data
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"validReleaseDate"})
@NoArgsConstructor
public class Film {

    // делаем поле приватным, чтобы оно не попало в json-объект
    // а также помечаем аннотацией, чтобы не создавался геттер для этого поля, чтобы также не попало в json
    @Getter(AccessLevel.NONE)
    private final LocalDate filmMinReleaseDate = LocalDate.of(1895, 12, 18);

    // с помощью groups и маркерного интерфейса Update, мы проверяем наличие id только
    // на моменте PUT в контроллере
    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class})
    Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    String name;

    @Size(max = 200, message = "Длина описания фильма не может превышать 200 символов")
    String description;

    @NotNull(message = "Дата релиза фильма не может быть пустой")
    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    Integer duration;

    private Set<Long> likes = new HashSet<>();

    // возрастной рейтинг фильма
    @NotNull
    private Mpa mpa;
    private LinkedHashSet<Genre> genres;

    public Film(Long id, String name, String description, LocalDate releaseDate, Integer duration,
                Set<Long> likes, Mpa mpa, LinkedHashSet<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
        this.mpa = mpa;
        this.genres = genres;
    }

    /**
     * валидация входных данных в контроллерах
     */
    @AssertTrue(message = "Дата релиза фильма должна быть после 18.12.1895")
    public boolean isValidReleaseDate() {
        return releaseDate.isAfter(filmMinReleaseDate);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("releaseDate", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }

}


//{
//        "name": "Гарфилд",
//        "description": "Фильм про рыжего кота",
//        "releaseDate": "2008-10-10",
//        "duration": 88
//        }