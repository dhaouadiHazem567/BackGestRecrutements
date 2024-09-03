package org.example.gestrecrutements.repository;

import org.example.gestrecrutements.entity.Role;
import org.example.gestrecrutements.entity.User;
import org.example.gestrecrutements.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    <T extends User> List<T> findUsersByRolesContains(Role role);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Optional<User> findUserByUsernameOrEmail(String username, String email);

}
