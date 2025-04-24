package edu.mondragon.webeng1.mvc_exercise.domain.sportResult.repository;

import java.util.ArrayList;

import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.model.SportResult;

public interface SportResultRepository {
    public SportResult insertSportResult(SportResult sportResult);
    public SportResult loadSportResult(int sportResultId);
    public ArrayList<SportResult> loadSportResults();
    public SportResult updateSportResult(SportResult sportResult);
    public boolean deleteSportResult(int sportResultId);
}
