package org.example.gestrecrutements.repository;

import org.example.gestrecrutements.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreRepository extends JpaRepository<Offre,Long> {

    List<Offre> findOffresByHrManager_Id(long idHRManager);
}
