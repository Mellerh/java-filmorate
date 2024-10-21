package ru.yandex.practicum.filmorate.repository.mpaRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JdbcMpaRepository implements MpaRepository {

    private final NamedParameterJdbcOperations jdbc;

    private final String getMpaById = "SELECT * FROM mpa WHERE mpa_id = :mpa_id";
    private final String getAllMpa = "SELECT * FROM mpa";

    @Override
    public Mpa getMpaById(Integer mpaId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("mpa_id", mpaId);

        try {
            return jdbc.queryForObject(getMpaById, sqlParameterSource, new MpaRowMapper());
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return jdbc.query(getAllMpa, new MpaRowMapper());
    }
}
