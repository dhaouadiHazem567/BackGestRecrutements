package org.example.gestrecrutements.service;

import org.example.gestrecrutements.entity.Offre;

import java.util.List;

public interface OffreService {

    List<Offre> retrieveAllOffers();
    Offre retrieveOfferById(long id);
    Offre removeOfferById(long id, long idHRManager);
    Offre createOffer(Offre offre, long idHRManager);
    Offre updateOffre(Offre offre, long idHRManager);
    List<Offre> findOffresByHrManager(long idHRManager);

}
