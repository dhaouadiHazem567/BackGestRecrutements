package org.example.gestrecrutements.controller;


import org.example.gestrecrutements.entity.Offre;
import org.example.gestrecrutements.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offre")
public class OffreController {

    @Autowired
    OffreService offreService;

    @GetMapping("/retrieveAllOffers")
    public List<Offre> retrieveAllOffers(){
        return offreService.retrieveAllOffers();
    }

    @GetMapping("/retrieveOfferById/{id}")
    public Offre retrieveOfferById(@PathVariable long id){
        return offreService.retrieveOfferById(id);
    }

    @DeleteMapping("/removeOfferById/{id}/{idHRManager}")
    public ResponseEntity<String> removeOfferById(@PathVariable long id, @PathVariable long idHRManager){
        Offre offre = offreService.removeOfferById(id,idHRManager);
        if(offre == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/createOffer/{idHRManager}")
    public ResponseEntity<Offre> createOffer(@RequestBody Offre offre, @PathVariable long idHRManager){
        Offre createdOffre = offreService.createOffer(offre, idHRManager);
        if(createdOffre == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(createdOffre);
    }

    @PutMapping("/updateOffre/{idHRManager}")
    public ResponseEntity<Offre> updateOffre(@RequestBody Offre offre, @PathVariable long idHRManager){
        Offre updatedOffre = offreService.updateOffre(offre, idHRManager);
        if(updatedOffre == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedOffre);
    }

    @GetMapping("/findOffresByHrManager/{idHRManager}")
    public List<Offre> findOffresByHrManager(@PathVariable long idHRManager){
        return offreService.findOffresByHrManager(idHRManager);
    }
}
