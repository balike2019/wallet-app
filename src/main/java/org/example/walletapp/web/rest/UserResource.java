package org.example.walletapp.web.rest;


import org.example.walletapp.domain.User;
import org.example.walletapp.repository.UserRepository;
import org.example.walletapp.security.AuthoritiesConstants;
import org.example.walletapp.service.UpdateUserException;
import org.example.walletapp.service.UserService;
import org.example.walletapp.service.dto.AdminUserDTO;
import org.example.walletapp.service.dto.PasswordChangeDTO;
import org.example.walletapp.service.dto.UserDTO;
import org.example.walletapp.web.errors.InvalidPasswordException;
import org.example.walletapp.web.rest.errors.EmailNotFoundException;
import org.example.walletapp.web.rest.errors.InternalServerErrorException;
import org.example.walletapp.web.vm.KeyAndPasswordVM;
import org.example.walletapp.web.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;
    private final UserRepository userRepository;
  //  private final MailService mailService;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
      //  this.mailService = mailService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerAccount(@RequestBody AdminUserDTO userDTO) {
        User user = userService.registerUser(userDTO, userDTO.getPassword());
        return new UserDTO(user);
    }

    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    @PostMapping("/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        userService.requestPasswordReset(mail).orElseThrow(EmailNotFoundException::new);
    }

    @PostMapping("/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public Page<AdminUserDTO> getAllUsers(Pageable pageable) {
        return userService.getAllManagedUsers(pageable);
    }


    @GetMapping("/user/{login}")
    public UserDTO getUser(@PathVariable String login) {
        return new UserDTO(userService.getUserWithAuthoritiesByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @DeleteMapping("/user/{login}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public void deleteUser(@PathVariable String login) {
        userService.deleteUser(login);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody AdminUserDTO userDTO) {
        userService.updateUser(userDTO).orElseThrow(() -> new UpdateUserException("User could not be updated"));
    }

    @PutMapping("/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
    }

    private boolean checkPasswordLength(String password) {
        return password != null && password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH && password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
