package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.converter.CrmUserConverter;
import fom.pmse.crms.backend.dto.ErrorDto;
import fom.pmse.crms.backend.payload.request.ChangeUserDto;
import fom.pmse.crms.backend.payload.request.ResetPasswordDto;
import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.model.Role;
import fom.pmse.crms.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CrmUserDto> getMe(Authentication authentication) {
        log.info("Received request to get current user");
        String username = authentication.getName();
        CrmUserDto crmUserDto = userService.getUserByName(username);
        log.info("Fetched user {}", crmUserDto.getUsername());
        return ResponseEntity.ok(crmUserDto);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Returns list of all users")
    @ApiResponse(responseCode = "200", description = "List of users")
    public ResponseEntity<List<CrmUserDto>> getAllUsers() {
        log.info("Received request to get all users");
        List<CrmUserDto> crmUserDtos = userService.getAllUsers();
        log.info("Fetched {} users", crmUserDtos.size());
        return ResponseEntity.ok(crmUserDtos);
    }

    @PostMapping("/lock")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Locks a user. Unlocks if already locked. Returns the updated user.")
    @ApiResponse(responseCode = "200", description = "User locked/unlocked")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<?> lockUser(@NonNull @RequestParam String username) {
        CrmUser user = userService.lockUser(username);
        return ResponseEntity.ok().body(CrmUserConverter.toDto(user));
    }

    @PostMapping("/lock/me")
    @Operation(summary = "Locks the current user")
    @ApiResponse(responseCode = "200", description = "User locked")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<?> lockCurrentUser(Authentication authentication) {
        CrmUser user = userService.lockUser(authentication.getName(), false);
        CrmUserDto crmUserDto = CrmUserConverter.toDto(user);
        return ResponseEntity.ok().body(crmUserDto);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Changes the password of the current user")
    @ApiResponse(responseCode = "200", description = "Password changed")
    @ApiResponse(responseCode = "401", description = "New password is not valid", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @ApiResponse(responseCode = "403", description = "Old Password is not valid", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return userService.changePassword(resetPasswordDto);
    }

    @PutMapping("/change")
    @Operation(summary = "Changes the user", description = "Only the fields that are not null will be changed")
    @ApiResponse(responseCode = "200", description = "User changed", content = @Content(schema = @Schema(implementation = CrmUserDto.class)))
    @ApiResponse(responseCode = "404", description = "Username is empty or user not found")
    public ResponseEntity<?> changeUser(@RequestBody ChangeUserDto changeUserDto, Authentication authentication) {
        var isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()));
        log.info("Received request to change user {}", changeUserDto.getUsername());
        if (!isAdmin && !Objects.equals(changeUserDto.getUsername(), authentication.getName())) {
            log.info("User {} tried to change user {}", authentication.getName(), changeUserDto.getUsername());
            changeUserDto.setUsername(authentication.getName());
        }
        return userService.changeUser(changeUserDto);
    }
}
