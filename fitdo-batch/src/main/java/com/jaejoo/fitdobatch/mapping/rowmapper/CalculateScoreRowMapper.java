package com.jaejoo.fitdobatch.mapping.rowmapper;

import com.jaejoo.fitdobatch.mapping.CalculateScoreRow;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculateScoreRowMapper implements RowMapper<CalculateScoreRow> {
    @Override
    public CalculateScoreRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        System.out.println("usermapper");
        return new CalculateScoreRow(
                rs.getLong("userId"),
                rs.getInt("userWeight"),
                rs.getInt("userHeight"),
                rs.getInt("recordWeight"),
                rs.getInt("recordVolume"),
                rs.getBoolean("isProgress"),
                BodyPart.valueOf((String) rs.getObject("bodyPart"))
        );
    }
}
