package org.example.gestrecrutements.repository;

import org.example.gestrecrutements.entity.Role;
import org.example.gestrecrutements.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByRoleName(RoleName roleName);

}
