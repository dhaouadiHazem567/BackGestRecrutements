package org.example.gestrecrutements.repository;

import org.example.gestrecrutements.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

    Optional<Candidate> findCandidateByUsernameOrEmail(String username, String email);
    Optional<Candidate> findCandidateByUsername(String username);
}
