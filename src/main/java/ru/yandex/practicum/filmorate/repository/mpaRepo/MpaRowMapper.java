package ru.yandex.practicum.filmorate.repository.mpaRepo;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRowMapper implements RowMapper<Mpa> {


    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("name")
        );
    }
}
