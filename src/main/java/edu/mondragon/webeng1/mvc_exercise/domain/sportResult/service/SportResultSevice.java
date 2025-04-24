package edu.mondragon.webeng1.mvc_exercise.domain.sportResult.service;

import java.util.ArrayList;

import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.model.SportResult;
import edu.mondragon.webeng1.mvc_exercise.domain.sportResult.repository.SportResultRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SportResultSevice {
    @Inject
    private SportResultRepository repository;

    public SportResult loadSportResult(int sportResultId) {
        return repository.loadSportResult(sportResultId);
    }

    public ArrayList<SportResult> loadSportResults() {
        return repository.loadSportResults();
    }

    public SportResult saveSportResult(SportResult sportResult) {
        SportResult retSportResult = null;
        if (sportResult.getSportResultId() == 0) {
            retSportResult = repository.insertSportResult(sportResult);
        } else {
            // update
        }
        return retSportResult;
    }

    public boolean deleteSportResult(int sportResultId) {
        return repository.deleteSportResult(sportResultId);
    }
}
