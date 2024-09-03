package org.example.gestrecrutements.service;

import lombok.extern.slf4j.Slf4j;
import org.example.gestrecrutements.entity.Candidate;
import org.example.gestrecrutements.entity.Education;
import org.example.gestrecrutements.entity.Experience;
import org.example.gestrecrutements.entity.Role;
import org.example.gestrecrutements.enums.RoleName;
import org.example.gestrecrutements.repository.CandidateRepository;
import org.example.gestrecrutements.repository.EducationRepository;
import org.example.gestrecrutements.repository.ExperienceRepository;
import org.example.gestrecrutements.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService{

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<Candidate> retrieveAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate createCandidate(Candidate candidate) throws RuntimeException {

        Optional<Candidate> optionalCandidate = candidateRepository.findCandidateByUsernameOrEmail(
                candidate.getUsername(), candidate.getEmail());
        if (optionalCandidate.isPresent())
            return null;

        candidate.setPassword(bCryptPasswordEncoder.encode(candidate.getPassword()));

        if(candidate.getEducations() != null){
            for(Education education: candidate.getEducations())
                education.setCandidate(candidate);
        }

        if(candidate.getExperiences() != null)
        {
            for (Experience experience : candidate.getExperiences())
                experience.setCandidate(candidate);
        }

        Role role = roleRepository.findRoleByRoleName(RoleName.ROLE_CANDIDATE);
        if(role == null){
            role = new Role();
            role.setRoleName(RoleName.ROLE_CANDIDATE);
            roleRepository.save(role);
        }
        candidate.setRoles(new HashSet<>());
        candidate.getRoles().add(role);
        candidate.setCreatedAt(LocalDateTime.now());

        return candidateRepository.save(candidate);
    }

    @Override
    @Transactional
    public Candidate updateCandidate(Candidate candidate) throws RuntimeException{

        Optional<Candidate> optionalCandidate = candidateRepository.findById(candidate.getId());
        if(optionalCandidate.isEmpty())
            return null;

        Candidate candidate1 = optionalCandidate.get();

        Candidate candidate2 = candidateRepository.findCandidateByUsernameOrEmail(candidate.getUsername(),
                candidate.getEmail()).orElse(null);
        if(candidate2 != null && candidate1.getId() != candidate2.getId())
            return null;

        if(candidate.getEducations() != null){
            for(Education education: candidate.getEducations())
                education.setCandidate(candidate);
        }

        if(candidate.getExperiences() != null) {
            for (Experience experience : candidate.getExperiences())
                experience.setCandidate(candidate);
        }

        candidate.setPassword(bCryptPasswordEncoder.encode(candidate.getPassword()));
        candidate.setUpdatedAt(LocalDateTime.now());
        return candidateRepository.save(candidate);
    }

    @Override
    @Transactional
    public void removeCandidateById(long id) {
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if(candidate != null){
            candidate.setRoles(null);
            candidateRepository.deleteById(id);
        }
    }

    @Override
    public Candidate retrieveCandidateById(long id) {
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if(candidate == null)
            return null;

        return candidate;
    }

    @Override
    public Candidate retrieveCandidateByUsername(String username) {
        Optional<Candidate> optionalCandidate = candidateRepository.findCandidateByUsername(username);
        if(optionalCandidate.isPresent())
            return optionalCandidate.get();
        else
            return null;
    }
}
