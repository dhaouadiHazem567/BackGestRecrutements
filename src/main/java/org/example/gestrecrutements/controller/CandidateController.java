package org.example.gestrecrutements.controller;


import org.example.gestrecrutements.entity.Candidate;
import org.example.gestrecrutements.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @GetMapping("/retrieveAllCandidates")
    public List<Candidate> retrieveAllCandidates() {
        return candidateService.retrieveAllCandidates();
    }

    @PostMapping("/createCandidate")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate createdCandidate = candidateService.createCandidate(candidate);
        if(createdCandidate == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(createdCandidate);
    }

    @PutMapping("/updateCandidate")
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) {
        Candidate updatedCandidate = candidateService.updateCandidate(candidate);
        if(updatedCandidate == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCandidate);
    }

    @DeleteMapping("/removeCandidateById/{id}")
    public void removeCandidateById(@PathVariable long id) {
        candidateService.removeCandidateById(id);
    }

    @GetMapping("/retrieveCandidateById/{id}")
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public Candidate retrieveCandidateById(@PathVariable long id) {
        return candidateService.retrieveCandidateById(id);
    }

    @GetMapping("/retrieveCandidateByUsername/{username}")
    public Candidate retrieveCandidateByUsername(@PathVariable String username) {
        return candidateService.retrieveCandidateByUsername(username);
    }

}