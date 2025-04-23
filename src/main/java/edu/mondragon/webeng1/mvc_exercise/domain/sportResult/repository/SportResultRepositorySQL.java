package edu.mondragon.webeng1.mvc_exercise.domain.sportResult.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mondragon.webeng1.mvc_exercise.config.SQLConfig;
import edu.mondragon.webeng1.mvc_exercise.domain.user.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.mondragon.webeng1.mvc_exercise.config.SQLConfig;
import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.model.SportResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SportResultRepositorySQL implements SportResultRepository {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private SQLConfig sqlConfig;

    @Override
    public ArrayList<SportResult> loadSportResults() {
        ArrayList<SportResult> sportResultList = new ArrayList<SportResult>();
        Connection connection = sqlConfig.connect();
        String sqlQuery = "SELECT * FROM sport_result";

        ResultSet rs = null;
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sqlQuery);
            rs = stm.executeQuery();
            while (rs.next()) {
                SportResult sportResult = new SportResult();
                sportResult.setSportResultId(rs.getInt("sportResultId"));
                sportResult.setTeam1Name(rs.getString("team1Name"));
                sportResult.setTeam1Result(rs.getInt("team1Result"));
                sportResult.setTeam2Name(rs.getString("team2Name"));
                sportResult.setTeam2Result(rs.getInt("team2Result"));

                sportResultList.add(sportResult);
            }
        } catch (SQLException e) {
            logger.error("Error DaoUserMysql loadSportResult",e);
        }
        sqlConfig.disconnect(connection, stm);
        return sportResultList;
    }
}
