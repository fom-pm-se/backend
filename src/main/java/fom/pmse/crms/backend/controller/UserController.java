package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.converter.CrmUserConverter;
import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<CrmUserDto> getMe(Authentication authentication) {
        log.info("Received request to get current user");
        String username = authentication.getName();
        CrmUserDto crmUserDto = userService.getUserByName(username);
        if (crmUserDto == null) {
            log.warn("User {} not found", username);
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Locks a user. Unlocks if already locked. Returns the updated user.")
    @ApiResponse(responseCode = "200", description = "User locked/unlocked")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<?> lockUser(@RequestParam String username) {
        if (username == null || username.isEmpty()) {
            log.warn("Username is empty");
            return ResponseEntity.badRequest().body("Username is empty");
        }
        CrmUser user = userService.lockUser(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        CrmUserDto crmUserDto = CrmUserConverter.toDto(user);
        return ResponseEntity.ok().body(crmUserDto);
    }
}
