package org.example.gestrecrutements.service;

import org.example.gestrecrutements.entity.Application;
import org.example.gestrecrutements.entity.ApplicationPK;
import org.example.gestrecrutements.enums.ApplicationStatus;
import org.example.gestrecrutements.enums.FileType;
import org.example.gestrecrutements.repository.ApplicationRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ApplicationService {

    List<Application> retrieveAllApplications();
    Application createApplication(long idOffre, long idCandidate, MultipartFile fileCV,
                                  MultipartFile fileMotivationalLetter) throws IOException;
    Application updateApplicationStatus(long idOffre, long idCandidate, ApplicationStatus applicationStatus);
    void removeApplication(long idOffre, long idCandidate);
    byte[] getFile(long idOffre, long idCandidate, FileType fileType) throws IOException;
    Application retrieveApplicationById(ApplicationPK applicationPK);
    List<Application> getApplicationsByOffer(long idOffre);
}
