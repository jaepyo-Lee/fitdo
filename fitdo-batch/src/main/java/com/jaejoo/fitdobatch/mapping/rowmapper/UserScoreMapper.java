package com.jaejoo.fitdobatch.mapping.rowmapper;

import com.jaejoo.fitdobatch.mapping.UserScoreRow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserScoreMapper implements RowMapper<UserScoreRow> {
    @Override
    public UserScoreRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserScoreRow(
                rs.getLong("userId"),
                rs.getDouble("score")
        );
    }
}
