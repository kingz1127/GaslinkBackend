package com.gaslink.api.modules.address;
import com.gaslink.api.modules.address.dto.AddressDto;
import com.gaslink.api.modules.address.dto.CreateAddressRequest;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressDto> getAddresses(UUID userId) {
        return addressRepository.findByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AddressDto createAddress(UUID userId, CreateAddressRequest req) {
        if (req.isDefault()) addressRepository.clearDefaultForUser(userId);
        Address a = Address.builder().userId(userId).label(req.getLabel()).addressLine(req.getAddressLine())
                .city(req.getCity()).state(req.getState()).lat(req.getLat()).lng(req.getLng()).isDefault(req.isDefault()).build();
        return toDto(addressRepository.save(a));
    }

    @Transactional
    public AddressDto updateAddress(UUID userId, UUID id, CreateAddressRequest req) {
        Address a = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        if (!a.getUserId().equals(userId)) throw new ForbiddenException("Access denied");
        if (req.isDefault()) addressRepository.clearDefaultForUser(userId);
        a.setLabel(req.getLabel()); a.setAddressLine(req.getAddressLine());
        a.setCity(req.getCity()); a.setState(req.getState());
        a.setLat(req.getLat()); a.setLng(req.getLng()); a.setDefault(req.isDefault());
        return toDto(addressRepository.save(a));
    }

    @Transactional
    public void deleteAddress(UUID userId, UUID id) {
        Address a = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        if (!a.getUserId().equals(userId)) throw new ForbiddenException("Access denied");
        addressRepository.delete(a);
    }

    @Transactional
    public AddressDto setDefault(UUID userId, UUID id) {
        addressRepository.clearDefaultForUser(userId);
        Address a = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        if (!a.getUserId().equals(userId)) throw new ForbiddenException("Access denied");
        a.setDefault(true);
        return toDto(addressRepository.save(a));
    }

    private AddressDto toDto(Address a) {
        return AddressDto.builder().id(a.getId()).label(a.getLabel()).addressLine(a.getAddressLine())
                .city(a.getCity()).state(a.getState()).lat(a.getLat()).lng(a.getLng()).isDefault(a.isDefault()).build();
    }
}