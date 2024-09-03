package org.example.gestrecrutements.service;

import org.example.gestrecrutements.entity.Application;
import org.example.gestrecrutements.entity.ApplicationPK;
import org.example.gestrecrutements.entity.Candidate;
import org.example.gestrecrutements.entity.Offre;
import org.example.gestrecrutements.enums.ApplicationStatus;
import org.example.gestrecrutements.enums.FileType;
import org.example.gestrecrutements.repository.ApplicationRepository;
import org.example.gestrecrutements.repository.CandidateRepository;
import org.example.gestrecrutements.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService{

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    OffreRepository offreRepository;

    @Autowired
    EmailService emailService;

    @Override
    public List<Application> retrieveAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application createApplication(long idOffre, long idCandidate,
                                         MultipartFile fileCV, MultipartFile fileMotivationalLetter) throws IOException {

        Candidate candidate = candidateRepository.findById(idCandidate).orElse(null);
        if(candidate == null)
            return null;

        Offre offre = offreRepository.findById(idOffre).orElse(null);
        if(offre == null)
            return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        Path folderPath = Paths.get(System.getProperty("user.home"), "Recruitment", String.format(offre.getTitle()
                + "_" + offre.getCreatedAt().format(formatter)));
        if(!Files.exists(folderPath))
            Files.createDirectories(folderPath);

        String fileCVName = candidate.getFirstname()+candidate.getLastname()+"CV.pdf";
        Path fileCVPath = Paths.get(System.getProperty("user.home"), "Recruitment", String.format(offre.getTitle()
                + "_" + offre.getCreatedAt().format(formatter)) , fileCVName);
        Files.copy(fileCV.getInputStream(), fileCVPath, StandardCopyOption.REPLACE_EXISTING);

        String fileMotivationalLetterName = candidate.getFirstname()+candidate.getLastname()+"ML.pdf";
        Path fileMotivationalLetterPath = Paths.get(System.getProperty("user.home"), "Recruitment", String.format(offre.getTitle()
                + "_" + offre.getCreatedAt().format(formatter)) , fileMotivationalLetterName);
        Files.copy(fileMotivationalLetter.getInputStream(), fileMotivationalLetterPath, StandardCopyOption.REPLACE_EXISTING);

        Application application = new Application();
        application.setApplicationPK(new ApplicationPK(idCandidate,idOffre));
        application.setOffre(offre);
        application.setCandidate(candidate);
        application.setApplicationStatus(ApplicationStatus.PENDING);
        application.setFileCV(fileCVPath.toUri().toString());
        application.setMotivationalLetter(fileMotivationalLetterPath.toUri().toString());
        application.setCreatedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    @Override
    public Application updateApplicationStatus(long idOffre, long idCandidate, ApplicationStatus applicationStatus) {
        Application application = applicationRepository.findById(new ApplicationPK(idCandidate, idOffre))
                .orElse(null);
        if(application == null)
            return null;

        Candidate candidate = candidateRepository.findById(idCandidate).get();
        Offre offre = offreRepository.findById(idOffre).get();

        application.setApplicationStatus(applicationStatus);
        application.setUpdatedAt(LocalDateTime.now());

        if(applicationStatus.equals(ApplicationStatus.REJECTED))
            emailService.sendRejectionEmail(candidate.getEmail(), candidate.getFirstname(), offre.getTitle());
        else if(applicationStatus.equals(ApplicationStatus.ACCEPTED))
            emailService.sendAcceptanceEmail(candidate.getEmail(), candidate.getFirstname(), offre.getTitle());
        return applicationRepository.save(application);
    }

    @Override
    public void removeApplication(long idOffre, long idCandidate) {
        applicationRepository.deleteById(new ApplicationPK(idCandidate, idOffre));
    }

    @Override
    public byte[] getFile(long idOffre, long idCandidate, FileType fileType) throws IOException {
        Application application = applicationRepository.findById(new ApplicationPK(idCandidate,idOffre))
                .get();
        if(fileType.equals(FileType.CV))
            return Files.readAllBytes(Path.of(URI.create(application.getFileCV())));
        else if(fileType.equals(FileType.MOTIVATIONAL_LETTER))
            return Files.readAllBytes(Path.of(URI.create(application.getMotivationalLetter())));
        return null;
    }

    @Override
    public Application retrieveApplicationById(ApplicationPK applicationPK) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationPK);
        if(optionalApplication.isEmpty())
            return null;

        return optionalApplication.get();
    }

    @Override
    public List<Application> getApplicationsByOffer(long idOffre) {
        Offre offre = offreRepository.findById(idOffre).orElse(null);
        if(offre == null)
            return null;

        return applicationRepository.findApplicationsByOffre_Id(idOffre);
    }
}
