package org.example.walletapp.service;

import org.example.walletapp.domain.User;
import org.example.walletapp.service.dto.AdminUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> activateRegistration(String activationKey);
    List<String> getAuthorities();
     void removeNotActivatedUsers();
    Optional<User> getUserWithAuthorities();
    Optional<User> getUserWithAuthoritiesByLogin(String login);
    Page<AdminUserDTO> getAllManagedUsers(Pageable pageable);
    void deleteUser(String login) ;
    Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO);
    User createUser(AdminUserDTO userDTO);
    User registerUser(AdminUserDTO userDTO, String password);
    Optional<User> requestPasswordReset(String mail);
    Optional<User> completePasswordReset(String newPassword, String key);
    public void changePassword(String currentClearTextPassword, String newPassword);
}
