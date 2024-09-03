package org.example.gestrecrutements.service;

import org.example.gestrecrutements.dto.ForgotPasswordRequest;
import org.example.gestrecrutements.dto.PasswordRequest;
import org.example.gestrecrutements.entity.Admin;
import org.example.gestrecrutements.entity.HRManager;
import org.example.gestrecrutements.entity.Role;
import org.example.gestrecrutements.entity.User;
import org.example.gestrecrutements.enums.RoleName;

import java.util.List;

public interface AdminService {

    List<User> retrieveAllUsers();
    User retrieveUserById(long id);
    void removeUserById(long id);
    Admin addAdmin(Admin admin);
    HRManager addHrManager(HRManager hrManager);
    <T extends User> T updateUser(T user);
    <T extends User> List<T> findUsersByRole(RoleName roleName);
    User updateRole(long idUser, RoleName roleName);
    User updatePassword(PasswordRequest passwordRequest);
    User createCode(String email);
    User newPassword(ForgotPasswordRequest forgotPasswordRequest);
}
