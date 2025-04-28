package edu.mondragon.webeng1.mvc_exercise.domain.sportResult.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public SportResult insertSportResult(SportResult sportResult) {
        SportResult retSportResult = null;

        String sqlInsert = "INSERT INTO sport_result (team1Name,team1Result,team2Name,team2Result) VALUES(?,?,?,?)";

        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, sportResult.getTeam1Name());
            stm.setInt(2, sportResult.getTeam1Result());
            stm.setString(3, sportResult.getTeam2Name());
            stm.setInt(4, sportResult.getTeam2Result());
            logger.debug(stm.toString());
            if (stm.executeUpdate() > 0) {
                // Get the ID
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sportResult.setSportResultId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating News Item failed, no ID obtained.");
                    }
                }
                retSportResult = sportResult;
            } else {
                throw new SQLException("Creating News Item failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error(sqlInsert, e);
        }
        sqlConfig.disconnect(connection, stm);
        return retSportResult;
    }

    @Override
    public SportResult loadSportResult(int sportResultId) {
        String sqlQuery = "SELECT * FROM sport_result WHERE sportResultId=?";
        SportResult sportResult = null;
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, sportResultId);
            logger.info(stm.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                sportResult = new SportResult();
                sportResult.setSportResultId(rs.getInt("sportResultId"));
                sportResult.setTeam1Name(rs.getString("team1Name"));
                sportResult.setTeam1Result(rs.getInt("team1Result"));
                sportResult.setTeam2Name(rs.getString("team2Name"));
                sportResult.setTeam2Result(rs.getInt("team2Result"));
            }
        } catch (SQLException e) {
            logger.error("Error loadSportResult " + sportResultId);
        }
        sqlConfig.disconnect(connection, stm);
        return sportResult;
    }

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
            logger.error("Error DaoUserMysql loadSportResult", e);
        }
        sqlConfig.disconnect(connection, stm);
        return sportResultList;
    }

    @Override
    public SportResult updateSportResult(SportResult sportResult) {
        String sqlUpdate = "UPDATE sport_result SET team1Name=?, team1Result=?, team2Name=?, team2Result=?  WHERE sportResultId=?";
        SportResult retSportResult = null;
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlUpdate);
            stm.setString(1, sportResult.getTeam1Name());
            stm.setInt(2, sportResult.getTeam1Result());
            stm.setString(3, sportResult.getTeam2Name());
            stm.setInt(4, sportResult.getTeam2Result());
            stm.setInt(5, sportResult.getSportResultId());
            logger.debug(stm.toString());

            if (stm.executeUpdate() < 1) {
                sportResult.setSportResultId(0);
            }
            retSportResult = sportResult;
        } catch (SQLException e) {
            logger.error("Error updateUser " + sportResult.getSportResultId(), e);
        }
        sqlConfig.disconnect(connection, stm);
        return retSportResult;
    }

    @Override
    public boolean deleteSportResult(int sportResultId) {
        boolean ret = false;
        String sqlDelete = "DELETE FROM sport_result WHERE sportResultId=?";
        Connection connection = sqlConfig.connect();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sqlDelete);
            stm.setInt(1, sportResultId);
            if (stm.executeUpdate() > 0) {
                ret = true;
            } else {
                logger.error("Could not delete sport_result " + sportResultId);
            }
        } catch (SQLException e) {
            logger.error("Error deleteSportResult " + sportResultId);
        }

        return ret;
    }
}
