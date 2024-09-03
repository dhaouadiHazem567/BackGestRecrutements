package org.example.gestrecrutements.repository;

import org.example.gestrecrutements.entity.HRManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HRManagerRepository extends JpaRepository<HRManager,Long> {
}
