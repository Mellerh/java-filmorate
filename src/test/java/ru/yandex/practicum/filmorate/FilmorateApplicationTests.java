package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.filmLikesRepository.JdbcFilmLikesRepository;
import ru.yandex.practicum.filmorate.repository.filmRepo.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.repository.userRepo.JdbcUserRepository;
import ru.yandex.practicum.filmorate.service.filmService.FilmService;
import ru.yandex.practicum.filmorate.service.userService.UserService;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureCache
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final JdbcUserRepository jdbcUserRepository;
	private final JdbcFilmRepository jdbcFilmRepository;
	private final JdbcFilmLikesRepository jdbcFilmLikesRepository;
	private final FilmService filmService;
	private final UserService userService;
	private User firstUser = new User();
	private User secondUser = new User();
	private final User thirdUser = new User();
	private Film firstFilm = new Film();
	private Film secondFilm = new Film();
	private Film thirdFilm = new Film();

	@BeforeEach
	public void beforeEach() {
		firstUser.setName("firstUser");
		firstUser.setLogin("firstLogin");
		firstUser.setEmail("first@yandex.ru");
		firstUser.setBirthday(LocalDate.of(2000, 01, 1));

		secondUser.setName("secondUser");
		secondUser.setLogin("secondLogin");
		secondUser.setEmail("second@yandex.ru");
		secondUser.setBirthday(LocalDate.of(2000, 01, 1));

		thirdUser.setName("thirdUser");
		thirdUser.setLogin("thirdLogin");
		thirdUser.setEmail("third@yandex.ru");
		thirdUser.setBirthday(LocalDate.of(2000, 01, 1));

		firstFilm.setName("firstFilm");
		firstFilm.setDescription("firstFilm desc");
		firstFilm.setReleaseDate(LocalDate.of(2000, 01, 1));
		firstFilm.setDuration(100);
		firstFilm.setMpa(new Mpa(1, "G"));
		firstFilm.setLikes(new HashSet<>());
		firstFilm.setGenres(new LinkedHashSet<>(Arrays.asList(new Genre(1, "Комедия"),
				new Genre(2, "Драма"))));

		secondFilm.setName("secondFilm");
		secondFilm.setDescription("secondFilm desc");
		secondFilm.setReleaseDate(LocalDate.of(2000, 01, 1));
		secondFilm.setDuration(100);
		secondFilm.setMpa(new Mpa(1, "G"));
		secondFilm.setLikes(new HashSet<>());
		secondFilm.setGenres(new LinkedHashSet<>(Arrays.asList(new Genre(1, "Комедия"),
				new Genre(2, "Драма"))));

		thirdFilm.setName("thirdFilm");
		thirdFilm.setDescription("thirdFilm desc");
		thirdFilm.setReleaseDate(LocalDate.of(2000, 01, 1));
		thirdFilm.setDuration(100);
		thirdFilm.setMpa(new Mpa(1, "G"));
		thirdFilm.setLikes(new HashSet<>());
		thirdFilm.setGenres(new LinkedHashSet<>(Arrays.asList(new Genre(1, "Комедия"),
				new Genre(2, "Драма"))));
	}

	@Test
	public void testCreateUserAndGetUserById() {
		firstUser = jdbcUserRepository.saveUser(firstUser);
		Optional<User> userOptional = Optional.ofNullable(jdbcUserRepository.getUserById(firstUser.getId()));
		assertThat(userOptional)
				.hasValueSatisfying(user ->
						assertThat(user)
								.hasFieldOrPropertyWithValue("id", firstUser.getId())
								.hasFieldOrPropertyWithValue("name", "firstUser"));
	}

	@Test
	public void testGetUsers() {
		firstUser = jdbcUserRepository.saveUser(firstUser);
		secondUser = jdbcUserRepository.saveUser(secondUser);
		Collection<User> listUsers = jdbcUserRepository.getAllUsers();
		AssertionsForInterfaceTypes.assertThat(listUsers).contains(firstUser);
		AssertionsForInterfaceTypes.assertThat(listUsers).contains(secondUser);
	}

	@Test
	public void testUpdateUser() {
		firstUser = jdbcUserRepository.saveUser(firstUser);

		User updateUser = new User();
		updateUser.setId(firstUser.getId());
		updateUser.setName("UpdateFirst");
		updateUser.setLogin("thirdLogin");
		updateUser.setEmail("third@yandex.ru");
		updateUser.setBirthday(LocalDate.of(2000, 1, 1));

		Optional<User> testUpdateUser = Optional.ofNullable(jdbcUserRepository.updateUser(updateUser));
		assertThat(testUpdateUser)
				.hasValueSatisfying(user -> assertThat(user)
						.hasFieldOrPropertyWithValue("name", "UpdateFirst")
				);
	}


	@Test
	public void testCreateFilmAndGetFilmById() {
		firstFilm = jdbcFilmRepository.saveFilm(firstFilm);
		Optional<Film> filmOptional = Optional.ofNullable(jdbcFilmRepository.getFilmById(firstFilm.getId()));
		assertThat(filmOptional)
				.hasValueSatisfying(film -> assertThat(film)
						.hasFieldOrPropertyWithValue("id", firstFilm.getId())
						.hasFieldOrPropertyWithValue("name", "firstFilm")
				);
	}

	@Test
	public void testGetFilms() {
		firstFilm = jdbcFilmRepository.saveFilm(firstFilm);
		secondFilm = jdbcFilmRepository.saveFilm(secondFilm);
		thirdFilm = jdbcFilmRepository.saveFilm(thirdFilm);
		List<Film> listFilms = jdbcFilmRepository.getAllFilms();
		AssertionsForInterfaceTypes.assertThat(listFilms).contains(firstFilm);
		AssertionsForInterfaceTypes.assertThat(listFilms).contains(secondFilm);
		AssertionsForInterfaceTypes.assertThat(listFilms).contains(thirdFilm);
	}

	@Test
	public void testUpdateFilm() {
		firstFilm = jdbcFilmRepository.saveFilm(firstFilm);
		Film updateFilm = new Film();
		updateFilm.setId(firstFilm.getId());
		updateFilm.setName("UpdateName");
		updateFilm.setDescription("UpdateDescription");
		updateFilm.setReleaseDate(LocalDate.of(1997, 2, 2));
		updateFilm.setDuration(120);
		updateFilm.setMpa(new Mpa(1, "G"));

		Optional<Film> testUpdateFilm = Optional.ofNullable(jdbcFilmRepository.updateFilm(updateFilm));
		assertThat(testUpdateFilm)
				.hasValueSatisfying(film ->
						assertThat(film)
								.hasFieldOrPropertyWithValue("name", "UpdateName")
								.hasFieldOrPropertyWithValue("description", "UpdateDescription")
				);
	}


	@Test
	public void testAddLike() {
		firstUser = jdbcUserRepository.saveUser(firstUser);
		firstFilm = jdbcFilmRepository.saveFilm(firstFilm);
		filmService.addFilmLikeByUser(firstFilm.getId(), firstUser.getId());
		firstFilm = jdbcFilmRepository.getFilmById(firstFilm.getId());
		AssertionsForInterfaceTypes.assertThat(firstFilm.getLikes()).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(firstFilm.getLikes()).contains(firstUser.getId());
	}

	@Test
	public void testDeleteLike() {
		firstUser = jdbcUserRepository.saveUser(firstUser);
		secondUser = jdbcUserRepository.saveUser(secondUser);
		firstFilm = jdbcFilmRepository.saveFilm(firstFilm);
		filmService.addFilmLikeByUser(firstFilm.getId(), firstUser.getId());
		filmService.addFilmLikeByUser(firstFilm.getId(), secondUser.getId());
		filmService.deleteFilmLikeByUser(firstFilm.getId(), firstUser.getId());
		firstFilm = jdbcFilmRepository.getFilmById(firstFilm.getId());
		AssertionsForInterfaceTypes.assertThat(firstFilm.getLikes()).hasSize(1);
		AssertionsForInterfaceTypes.assertThat(firstFilm.getLikes()).contains(secondUser.getId());
	}


}



