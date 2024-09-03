package org.example.gestrecrutements.controller;


import org.example.gestrecrutements.entity.Application;
import org.example.gestrecrutements.entity.ApplicationPK;
import org.example.gestrecrutements.enums.ApplicationStatus;
import org.example.gestrecrutements.enums.FileType;
import org.example.gestrecrutements.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/retrieveAllApplications")
    public List<Application> retrieveAllApplications(){
        return applicationService.retrieveAllApplications();
    }

    @PostMapping(path = "/createApplication/{idOffre}/{idCandidate}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Application createApplication(@PathVariable long idOffre, @PathVariable long idCandidate,
                                         @RequestParam MultipartFile fileCV, @RequestParam MultipartFile fileMotivationalLetter) throws IOException {
        return applicationService.createApplication(idOffre, idCandidate, fileCV, fileMotivationalLetter);
    }

    @PutMapping("/updateApplicationStatus/{idOffre}/{idCandidate}/{applicationStatus}")
    public Application updateApplicationStatus(@PathVariable long idOffre, @PathVariable long idCandidate
            , @PathVariable ApplicationStatus applicationStatus){
        return applicationService.updateApplicationStatus(idOffre, idCandidate, applicationStatus);
    }

    @DeleteMapping("/removeApplication/{idOffre}/{idCandidate}")
    public void removeApplication(@PathVariable long idOffre, @PathVariable long idCandidate){
        applicationService.removeApplication(idOffre, idCandidate);
    }

    @GetMapping(path = "/getFile/{idOffre}/{idCandidate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getFile(@PathVariable long idOffre, @PathVariable long idCandidate, @RequestParam FileType fileType) throws IOException{
        return applicationService.getFile(idOffre, idCandidate, fileType);
    }

    @GetMapping("/retrieveApplicationById/{idCandidate}/{idOffre}")
    public Application retrieveApplicationById(@PathVariable long idCandidate, @PathVariable long idOffre){
        return applicationService.retrieveApplicationById(new ApplicationPK(idCandidate, idOffre));
    }

    @GetMapping("/getApplicationsByOffer/{idOffre}")
    public List<Application> getApplicationsByOffer(@PathVariable long idOffre){
        return applicationService.getApplicationsByOffer(idOffre);
    }
}
