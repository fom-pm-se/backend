package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.converter.CrmUserConverter;
import fom.pmse.crms.backend.exception.BadRequestException;
import fom.pmse.crms.backend.exception.InvalidPasswordException;
import fom.pmse.crms.backend.payload.request.ChangeUserDto;
import fom.pmse.crms.backend.payload.request.ResetPasswordDto;
import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.repository.UserRepository;
import fom.pmse.crms.backend.security.service.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CrmUserDto> getAllUsers() {
        log.info("Building user list");
        List<CrmUser> users = userRepository.findAll();
        List<CrmUserDto> userDtos = new ArrayList<>(List.of());
        for (CrmUser user : users) {
            userDtos.add(CrmUserDto.builder()
                    .username(user.getUsername())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .isEnabled(user.isEnabled())
                    .role(user.getRole().toString())
                    .build());
        }
        return userDtos;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public CrmUser lockUser(String username) {
        log.info("Locking user {}", username);
        CrmUser crmUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Benutzer konnte nicht gefunden werden " + username));
        return lockUser(username, !crmUser.isEnabled());
    }

    public CrmUser lockUser(String username, boolean enabled) {
        log.info("Locking user {}", username);
        CrmUser crmUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Benutzer konnte nicht gefunden werden " + username));
        log.info("Setting enabled to {}", enabled);
        crmUser.setEnabled(enabled);
        userRepository.save(crmUser);
        authenticationService.revokeAllUserTokens(crmUser);
        return crmUser;
    }

    public CrmUserDto getUserByName(String username) {
        log.info("Fetching user {}", username);
        CrmUser crmUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Benutzer konnte nicht gefunden werden " + username));
        log.info("Returning user {}", username);
        return CrmUserConverter.toDto(crmUser);
    }

    public ResponseEntity<?> changePassword(@NonNull ResetPasswordDto resetPasswordDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Changing password for user {}", username);
        CrmUser crmUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Benutzer konnte nicht gefunden werden " + username));
        if (!authenticationService.isPasswordValid(resetPasswordDto.getNewPassword())) {
            log.warn("Password is not valid for user {}", username);
            throw new InvalidPasswordException();
        }
        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), crmUser.getPassword())) {
            log.warn("Old password is not valid for user {}", username);
            throw new BadRequestException("Altes Passwort ist falsch");
        }
        crmUser.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(crmUser);
        log.info("Password changed successfully for user {}", username);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> changeUser(ChangeUserDto changeUserDto) {
        log.info("Changing user {}", changeUserDto.getUsername());
        CrmUser crmUser = userRepository.findByUsername(changeUserDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Benutzer konnte nicht gefunden werden " + changeUserDto.getUsername()));
        if (changeUserDto.getFirstname().length() < 2 || changeUserDto.getFirstname().length() > 50) {
            log.warn("Firstname is not valid for user {}", changeUserDto.getUsername());
            throw new BadRequestException("Vorname muss zwischen 2 und 50 Zeichen lang sein");
        }
        if (changeUserDto.getLastname().length() < 2 || changeUserDto.getLastname().length() > 50) {
            log.warn("Lastname is not valid for user {}", changeUserDto.getUsername());
            throw new BadRequestException("Nachname muss zwischen 2 und 50 Zeichen lang sein");
        }
        if (changeUserDto.getUsername().length() < 2 || changeUserDto.getUsername().length() > 50) {
            log.warn("Username is not valid for user {}", changeUserDto.getUsername());
            throw new BadRequestException("Benutzername muss zwischen 2 und 50 Zeichen lang sein");
        }
        crmUser.setFirstname(changeUserDto.getFirstname());
        crmUser.setLastname(changeUserDto.getLastname());
        crmUser.setUsername(changeUserDto.getUsername());
        log.info("Saving user {}", changeUserDto.getUsername());
        userRepository.save(crmUser);
        log.info("User {} changed successfully", changeUserDto.getUsername());
        return ResponseEntity.ok(CrmUserConverter.toDto(crmUser));
    }
}
