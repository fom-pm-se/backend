package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Slf4j
public class UserService {
    private final UserRepository userRepository;
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
}
