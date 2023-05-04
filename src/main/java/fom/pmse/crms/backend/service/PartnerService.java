package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.model.PartnerType;
import fom.pmse.crms.backend.payload.request.CreatePartnerDto;
import fom.pmse.crms.backend.payload.response.PartnerDto;
import fom.pmse.crms.backend.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public PartnerDto createPartner(CreatePartnerDto createPartnerDto) {
        partnerRepository.findByName(createPartnerDto.getName())
                .ifPresent(partner -> {
                    throw new IllegalArgumentException("Partner mit diesem Namen existiert bereits");
                });
        return PartnerDto.fromPartner(partnerRepository.save(createPartnerDto.toPartner()));
    }

    public void updatePartner(CreatePartnerDto createPartnerDto) {
        partnerRepository.findByName(createPartnerDto.getName())
                .ifPresentOrElse(
                        partner -> {
                            partner.setAddress(createPartnerDto.getAddress());
                            partner.setPhoneNumber(createPartnerDto.getPhoneNumber());
                            partner.setType(createPartnerDto.getType());
                            partnerRepository.save(partner);
                        },
                        () -> {
                            throw new IllegalArgumentException("Partner mit diesem Namen existiert nicht");
                        }
                );
    }

    public List<PartnerDto> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(PartnerDto::fromPartner)
                .toList();
    }

    public List<String> getAllPartnerTypes() {
        List<String> types = new ArrayList<String>();
        PartnerType.getTypes().forEach(type -> types.add(type.toString()));
        return types;
    }

}
