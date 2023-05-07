package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.model.PartnerType;
import fom.pmse.crms.backend.payload.request.CreatePartnerDto;
import fom.pmse.crms.backend.payload.response.PartnerDto;
import fom.pmse.crms.backend.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public PartnerDto createPartner(CreatePartnerDto createPartnerDto) {
        log.info("Creating new partner with name {}", createPartnerDto.getName());
        partnerRepository.findByName(createPartnerDto.getName())
                .ifPresent(partner -> {
                    log.error("Partner with name {} already exists", createPartnerDto.getName());
                    throw new IllegalArgumentException("Partner mit diesem Namen existiert bereits");
                });
        log.info("Partner with name {} does not exist yet", createPartnerDto.getName());
        return PartnerDto.fromPartner(partnerRepository.save(createPartnerDto.toPartner()));
    }

    public void updatePartner(CreatePartnerDto createPartnerDto) {
        log.info("Updating partner with name {}", createPartnerDto.getName());
        partnerRepository.findByName(createPartnerDto.getName())
                .ifPresentOrElse(
                        partner -> {
                            log.info("Partner with name {} exists. Updating properties and saving to database.", createPartnerDto.getName());
                            partner.setAddress(createPartnerDto.getAddress());
                            partner.setPhoneNumber(createPartnerDto.getPhoneNumber());
                            partner.setType(PartnerType.valueOf(createPartnerDto.getType()));
                            partnerRepository.save(partner);
                        },
                        () -> {
                            log.error("Partner with name {} does not exist. Cannot update properties.", createPartnerDto.getName());
                            throw new IllegalArgumentException("Partner mit diesem Namen existiert nicht");
                        }
                );
    }

    public List<PartnerDto> getAllPartners() {
        log.info("Building list of existing Partners from Database");
        return partnerRepository.findAll().stream()
                .map(PartnerDto::fromPartner)
                .toList();
    }

    public List<String> getAllPartnerTypes() {
        log.info("Building list of all PartnerTypes");
        List<String> types = new ArrayList<String>();
        PartnerType.getTypes().forEach(type -> types.add(type.toString()));
        return types;
    }

    public PartnerDto getPartnerById(Long id) {
        log.info("Building Partner with id {}", id);
        return PartnerDto.fromPartner(partnerRepository.findById(id).orElseThrow());
    }

}
