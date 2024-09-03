package org.example.gestrecrutements.service;

import org.example.gestrecrutements.entity.HRManager;
import org.example.gestrecrutements.entity.Offre;
import org.example.gestrecrutements.enums.OffreStatus;
import org.example.gestrecrutements.repository.HRManagerRepository;
import org.example.gestrecrutements.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OffreServiceImpl implements OffreService{

    @Autowired
    OffreRepository offreRepository;

    @Autowired
    HRManagerRepository hrManagerRepository;


    @Override
    public List<Offre> retrieveAllOffers() {
        return offreRepository.findAll();
    }

    @Override
    public Offre retrieveOfferById(long id) {
        Offre offre = offreRepository.findById(id).orElse(null);
        if(offre == null)
            return null;
        return offre;
    }

    @Override
    public Offre removeOfferById(long id, long idHRManager) throws RuntimeException{
        HRManager hrManager = hrManagerRepository.findById(idHRManager).orElse(null);
        if(hrManager == null)
            return null;

        Offre offre = offreRepository.findById(id).orElse(null);
        if(offre == null)
            return null;

        if(offre.getHrManager().getId() == idHRManager) {
            offreRepository.deleteById(id);
            return offre;
        }
        else
            return null;
    }

    @Override
    public Offre createOffer(Offre offre, long idHRManager) throws RuntimeException{
        HRManager hrManager = hrManagerRepository.findById(idHRManager).orElse(null);
        if(hrManager == null)
            return null;

        offre.setOffreStatus(OffreStatus.OPEN);
        offre.setCreatedAt(LocalDateTime.now());
        offre.setHrManager(hrManager);
        return offreRepository.save(offre);
    }

    @Override
    public Offre updateOffre(Offre offre, long idHRManager) {
        HRManager hrManager = hrManagerRepository.findById(idHRManager).orElse(null);
        if(hrManager == null)
            return null;

        if(offre.getHrManager().getId() == idHRManager)
        {
            // offre.setHrManager(hrManager);
            offre.setUpdatedAt(LocalDateTime.now());
            return offreRepository.save(offre);
        }
        else
            return null;
    }

    @Override
    public List<Offre> findOffresByHrManager(long idHRManager) {
        HRManager hrManager = hrManagerRepository.findById(idHRManager).orElse(null);
        if(hrManager == null)
            throw new RuntimeException("HRManager not found");

        return offreRepository.findOffresByHrManager_Id(idHRManager);
    }
}
