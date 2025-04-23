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

    public ArrayList<SportResult> loadSportResults() {
        return repository.loadSportResults();
    }
}
