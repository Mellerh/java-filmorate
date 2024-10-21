//package ru.yandex.practicum.filmorate.controller;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import org.junit.jupiter.api.*;
//import ru.yandex.practicum.filmorate.exception.exceptions.NotFoundException;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.repository.userRepo.InMemoryUserStorage;
//import ru.yandex.practicum.filmorate.repository.userRepo.UserRepository;
//import ru.yandex.practicum.filmorate.service.userService.UserServiceIml;
//
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class UserControllerTest {
//
//    // Если мы валидируем поля DTO через аннотации, то и в тестах нужно применять аннотации
//    // подключаем валидатор
//    private static Validator validator;
//
//    // сервис для хренения фильмов
//
//    private UserServiceIml userService;
//    private UserRepository userStorage;
//    private User user;
//
//
//    @BeforeAll
//    @DisplayName("Общий валидатор")
//    static void setUpValidator() {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//    }
//
//
//    @BeforeEach
//    @DisplayName("Инициализирующий метод")
//    void init() {
//        userService = new UserServiceIml(new InMemoryUserStorage());
//        user = new User();
//    }
//
//
//    @Test
//    @DisplayName("Метод проверяет корректность создания пользователя с валидными данными и добавлением в сервис хранеия")
//    void validateUserWithCorrectDate() {
//        defaultUserSettings();
//
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        Assertions.assertTrue(violations.isEmpty(), "Ожидается, что валидация пройдёт успешно");
//
//        userService.createNewUser(user);
//        Assertions.assertEquals(1, userService.getAllUsers().size(), "разрмер getAllUsers должно быть 1");
//
//        Assertions.assertEquals(user.getLogin(), user.getName(), "Login и Name должны совпадать");
//    }
//
//
//    @Test
//    @DisplayName("Метод проверяет корректность создания пользователя с невалидными данными")
//    void validateFilmWithUnCorrectDate() {
//        user.setLogin("");
//
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        Assertions.assertFalse(violations.isEmpty(), "Ожидается, что произойдёт ошибка валидации");
//    }
//
//
//    @Test
//    @DisplayName("Метод проверяет корректность генерации id для пользователя и последующего обновления пользователя по id")
//    void validateFilmIdAndUpdate() {
//        defaultUserSettings();
//
//        // получаем первый объект из коллекции
//        userService.createNewUser(user);
//        Collection<User> allUser = userService.getAllUsers();
//        User userToUpdate = allUser.iterator().next();
//
//
//        // обновляем name и обновляем значение в сервисе хрения
//        userToUpdate.setLogin("Алеандро");
//        userService.updateUser(userToUpdate);
//        Collection<User> allUser1 = userService.getAllUsers();
//        User updatedUser = allUser1.iterator().next();
//
//        assertEquals(1, updatedUser.getId(), "idGenerator генерирует неккоректный id");
//        assertEquals("Алеандро", updatedUser.getLogin(),
//                "Некорректный update пользователя по логину");
//
//    }
//
//
//    @Test
//    @DisplayName("Проверяем корректность срабатывания исключения при неправлиьном id")
//    void notFoundExceptionTest() {
//        defaultUserSettings();
//        userService.createNewUser(user);
//
//        user.setId(2L);
//
//        Assertions.assertThrows(NotFoundException.class, () -> {
//            userService.updateUser(user);
//        }, "Должно быть выброшено исключения из-за отсутсвия User с id 2");
//    }
//
//
//    /**
//     * стартовые настройки для пользователя
//     */
//    private void defaultUserSettings() {
//        user.setLogin("Sasha");
//        user.setEmail("sasha@yandex.ru");
//        user.setBirthday(LocalDate.of(2000, 1, 1));
//    }
//
//
//}