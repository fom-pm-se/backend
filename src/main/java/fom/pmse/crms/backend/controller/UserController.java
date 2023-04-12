package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.repository.UserRepository;
import fom.pmse.crms.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('ADMIN')")
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<CrmUserDto>> getAllUsers() {
        log.info("Received request to get all users");
        List<CrmUserDto> crmUserDtos = userService.getAllUsers();
        log.info("Fetched {} users", crmUserDtos.size());
        return ResponseEntity.ok(crmUserDtos);
    }
}
