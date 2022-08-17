package ru.job4j.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.CandidateDbStore;

import java.util.Collection;

@Service @ThreadSafe
public class CandidateService {
    private final CandidateDbStore store;
    private final CityService cityService;

    public CandidateService(CandidateDbStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        Collection<Candidate> candidates = store.findAll();
        candidates.forEach(
                candidate -> candidate.setCity(
                        cityService.findById(candidate.getCity().getId())));
        return candidates;
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        Candidate candidate = store.findById(id);
        if (candidate != null) {
            candidate.setCity(cityService.findById(candidate.getCity().getId()));
        }
        return candidate;
    }
}
