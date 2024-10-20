package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.model.helpres.Update;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class User {

    @NotNull(groups = {Update.class})
    @Positive(groups = {Update.class})
    Long id;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Email должен быть реальным")
    String email;

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелы")
    String login;

    // не проверяем, потому что может быть пустым
    String name;

    @NotNull
    @Past(message = "Дата рождения не может быть в будущем")
    LocalDate birthday;

    private Set<Long> friends;

    public User(Long id, String email, String login, String name, LocalDate birthday, Set<Long> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        if ((name == null) || (name.isEmpty()) || (name.isBlank())) {
            this.name = login;
        }
        this.birthday = birthday;
        this.friends = friends;
        if (friends == null) {
            this.friends = new HashSet<>();
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }

    /**
     * аннотация метода валидации позволяет создать кастомную проверку
     */
    @AssertTrue(message = "Имя для отображения будет изменено на логин, если оно пустое")
    private boolean isNameValid() {
        if (name == null || name.isEmpty()) {
            this.name = this.login;
        }

        return true;
    }

}

