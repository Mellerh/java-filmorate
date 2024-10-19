package ru.yandex.practicum.filmorate.repository.mpaRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JdbcMpaRepository implements MpaRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final String GET_MPA_BY_ID = "SELECT * FROM mpa WHERE mpa_id = :mpa_id";
    private final String GET_ALL_MPA = "SELECT * FROM mpa";

    @Override
    public Mpa getMpaById(Integer mpaId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("mpa_id", mpaId);

        try {
            return jdbc.queryForObject(GET_MPA_BY_ID, sqlParameterSource, new MpaRowMapper());
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }

    }

    @Override
    public Collection<Mpa> getAllMpa() {
        try {
            return jdbc.query(GET_ALL_MPA, new MpaRowMapper());
        } catch (EmptyResultDataAccessException ignored) {
            return null;
        }
    }
}
