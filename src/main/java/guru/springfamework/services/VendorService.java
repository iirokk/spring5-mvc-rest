package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<VendorDTO> getAllVendors() {
        return vendorRepository
                .findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDto)
                .collect(Collectors.toList());
    }

    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public VendorDTO createNewVendor(VendorDTO VendorDTO) {
        return persistVendor(vendorMapper.vendorDtoToVendor(VendorDTO));
    }

    public VendorDTO updateOrCreateVendor(Long id, VendorDTO VendorDTO) {
        Vendor VendorToPersist = vendorMapper.vendorDtoToVendor(VendorDTO);
        VendorToPersist.setId(id);
        return persistVendor(VendorToPersist);
    }

    private VendorDTO persistVendor(Vendor Vendor) {
        Vendor savedVendor = vendorRepository.save(Vendor);
        return vendorMapper.vendorToVendorDto(savedVendor);
    }

    public VendorDTO patchVendor(Long id, VendorDTO VendorDTO) {
        Vendor patchedVendor = vendorRepository.findById(id).map(Vendor -> {
            if (VendorDTO.getName() != null) {
                Vendor.setName(VendorDTO.getName());
            }
            return vendorRepository.save(Vendor);
        }).orElseThrow(ResourceNotFoundException::new);
        return vendorMapper.vendorToVendorDto(patchedVendor);
    }

    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
