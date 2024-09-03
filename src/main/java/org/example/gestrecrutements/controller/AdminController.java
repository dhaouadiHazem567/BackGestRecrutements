package org.example.gestrecrutements.controller;

import org.example.gestrecrutements.dto.ForgotPasswordRequest;
import org.example.gestrecrutements.dto.PasswordRequest;
import org.example.gestrecrutements.entity.Admin;
import org.example.gestrecrutements.entity.HRManager;
import org.example.gestrecrutements.entity.User;
import org.example.gestrecrutements.enums.RoleName;
import org.example.gestrecrutements.repository.UserRepository;
import org.example.gestrecrutements.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/retrieveAllUsers")
    public List<User> retrieveAllUsers(){
        return adminService.retrieveAllUsers();
    }

    @GetMapping("/retrieveUserById/{id}")
    public User retrieveUserById(@PathVariable long id){
        return adminService.retrieveUserById(id);
    }

    @DeleteMapping("/removeUserById/{id}")
    public ResponseEntity<String> removeUserById(@PathVariable long id){
        adminService.removeUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        Admin createdAdmin = adminService.addAdmin(admin);
        if(createdAdmin == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(createdAdmin);
    }

    @PostMapping("/createHRManager")
    public ResponseEntity<HRManager> createHRManager(@RequestBody HRManager hrManager){
        HRManager createdHrManager = adminService.addHrManager(hrManager);
        if(createdHrManager == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(createdHrManager);
    }

    @PutMapping("/updateAdmin")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin){
        Admin updatedAdmin = adminService.updateUser(admin);
        if(updatedAdmin == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }

    @PutMapping("/updateHRManager")
    public ResponseEntity<HRManager> updateHRManager(@RequestBody HRManager hrManager){
        HRManager updatedHrManager = adminService.updateUser(hrManager);
        if(updatedHrManager == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedHrManager);
    }

    @GetMapping("/findUsersByRole/{roleName}")
    public <T extends User> List<T> findUsersByRole(@PathVariable RoleName roleName){
        return adminService.findUsersByRole(roleName);
    }

    @PutMapping("/updateRole/{idUser}/{roleName}")
    public ResponseEntity<User> updateRole(@PathVariable long idUser, @PathVariable RoleName roleName){
        User updatedUser = adminService.updateRole(idUser, roleName);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PutMapping("/updatePassword")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<User> updatePassword(@RequestBody PasswordRequest passwordRequest){
        User updatedUser = adminService.updatePassword(passwordRequest);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PutMapping("/createCode")
    public ResponseEntity<User> createCode(@RequestBody String email){
        User updatedUser = adminService.createCode(email);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PutMapping("/newPassword")
    public ResponseEntity<User> newPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        User updatedUser = adminService.newPassword(forgotPasswordRequest);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
