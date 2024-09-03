package org.example.gestrecrutements.repository;

import org.example.gestrecrutements.entity.Application;
import org.example.gestrecrutements.entity.ApplicationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationPK> {

    List<Application> findApplicationsByOffre_Id(long idOffre);

}
