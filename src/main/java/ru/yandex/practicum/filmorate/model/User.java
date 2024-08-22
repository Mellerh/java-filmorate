package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.controller.Update;

import java.time.LocalDate;

@Data
public class User {

    @NotBlank(groups = {Update.class})
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

    /**
     * аннотация метода валидации позволяет создать кастомную проверку
     */
    @AssertTrue(message = "Имя для отображения будет изменено на логин, если оно пустое")
    public boolean isNameValid() {
        if (name == null || name.isEmpty()) {
            this.name = this.login;
        }

        return true;
    }


}
