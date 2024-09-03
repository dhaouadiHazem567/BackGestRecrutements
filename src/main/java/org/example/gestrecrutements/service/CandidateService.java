package org.example.gestrecrutements.service;

import org.example.gestrecrutements.entity.Candidate;

import java.util.List;

public interface CandidateService {

    List<Candidate> retrieveAllCandidates();
    Candidate createCandidate(Candidate candidate);
    Candidate updateCandidate(Candidate candidate);
    void removeCandidateById(long id);
    Candidate retrieveCandidateById(long id);
    Candidate retrieveCandidateByUsername(String username);
}
