package org.example.gestrecrutements.service;

import lombok.extern.slf4j.Slf4j;
import org.example.gestrecrutements.controller.AdminController;
import org.example.gestrecrutements.dto.ForgotPasswordRequest;
import org.example.gestrecrutements.dto.PasswordRequest;
import org.example.gestrecrutements.entity.*;
import org.example.gestrecrutements.enums.RoleName;
import org.example.gestrecrutements.repository.AdminRepository;
import org.example.gestrecrutements.repository.HRManagerRepository;
import org.example.gestrecrutements.repository.RoleRepository;
import org.example.gestrecrutements.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    HRManagerRepository hrManagerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User retrieveUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent())
            return optionalUser.get();
        return null;
    }

    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }

    public Admin addAdmin(Admin admin){

        Optional<User> optionalAdmin = userRepository.findUserByUsernameOrEmail(admin.getUsername(),
                admin.getEmail());
        if(optionalAdmin.isPresent())
            return null;
        log.info("Avant le role");
        Role role = roleRepository.findRoleByRoleName(RoleName.ROLE_ADMIN);
        if(role == null) {
            role = new Role();
            role.setRoleName(RoleName.ROLE_ADMIN);
            roleRepository.save(role);
        }
        log.info("Apres le role");
        if(admin.getRoles() == null)
            admin.setRoles(new HashSet<>());
        admin.getRoles().add(role);
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        admin.setCreatedAt(LocalDateTime.now());
        log.info("C BON");
        return adminRepository.save(admin);
    }

    public HRManager addHrManager(HRManager hrManager){
        Optional<User> optionalHrManager = userRepository.findUserByUsernameOrEmail(hrManager.getUsername(),
                hrManager.getEmail());
        if(optionalHrManager.isPresent())
            return null;

        Role role = roleRepository.findRoleByRoleName(RoleName.ROLE_HR_MANAGER);
        if(role == null) {
            role = new Role();
            role.setRoleName(RoleName.ROLE_HR_MANAGER);
            roleRepository.save(role);
        }

        if(hrManager.getRoles() == null)
            hrManager.setRoles(new HashSet<>());
        hrManager.getRoles().add(role);
        hrManager.setPassword(bCryptPasswordEncoder.encode(hrManager.getPassword()));
        hrManager.setCreatedAt(LocalDateTime.now());
        return hrManagerRepository.save(hrManager);
    }

    @Override
    public <T extends User> T updateUser(T user) {
        User u1 = userRepository.findById(user.getId()).orElse(null);
        if(u1 == null)
            return null;

        User u2  = userRepository.findUserByUsernameOrEmail(user.getUsername(), user.getEmail()).orElse(null);
        if(u2 != null && u1.getId() != u2.getId())
            return null;

        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(user.getClass().equals(Admin.class)) {
            user.setUpdatedAt(LocalDateTime.now());
            return (T) adminRepository.save((Admin) user);
        }
        else if(user.getClass().equals(HRManager.class)) {
            user.setUpdatedAt(LocalDateTime.now());
            return (T) hrManagerRepository.save((HRManager) user);
        }
        return null;
    }

    @Override
    public <T extends User> List<T> findUsersByRole(RoleName roleName) {
        Role role = roleRepository.findRoleByRoleName(roleName);
        return userRepository.findUsersByRolesContains(role);
    }

    @Override
    public User updateRole(long idUser, RoleName roleName) {
        User user = userRepository.findById(idUser).orElse(null);
        if(user == null)
            return null;

        User updatedUser = null;
        switch (roleName){
            case ROLE_ADMIN -> updatedUser = new Admin();
            case ROLE_HR_MANAGER -> updatedUser = new HRManager();
            case ROLE_CANDIDATE -> updatedUser = new Candidate();
        }

        updatedUser.setId(user.getId());
        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setGender(user.getGender());
        updatedUser.setBirthdate(user.getBirthdate());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setCreatedAt(user.getCreatedAt());
        updatedUser.setPhone(user.getPhone());
        if(user.getUpdatedAt() != null)
            updatedUser.setUpdatedAt(user.getUpdatedAt());
        Role role = roleRepository.findRoleByRoleName(roleName);
        updatedUser.setRoles(new HashSet<>());
        updatedUser.getRoles().add(role);

        user.setRoles(null);
        userRepository.deleteById(user.getId());
        return userRepository.save(updatedUser);
    }

    @Override
    public User updatePassword(PasswordRequest passwordRequest) {
        User user = userRepository.findById(passwordRequest.getIdUser()).orElse(null);
        if(user == null)
            return null;

        if(!bCryptPasswordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword()))
            return null;

        user.setPassword(bCryptPasswordEncoder.encode(passwordRequest.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User createCode(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null)
            return null;

        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(99999999);
        String body = "Your reset password code is : "+code;

        emailService.sendMail(email, "Reset Password", body);
        user.setCode(code);
        return userRepository.save(user);
    }

    @Override
    public User newPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User updatedUser = userRepository.findById(forgotPasswordRequest.getIdUser()).orElse(null);
        if(updatedUser == null)
            return null;

        updatedUser.setPassword(bCryptPasswordEncoder.encode(forgotPasswordRequest.getNewPassword()));
        return userRepository.save(updatedUser);
    }
}
