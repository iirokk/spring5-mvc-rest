package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        assertEquals(1, vendorDTOS.size());
        verify(vendorRepository).findAll();
    }

    @Test
    public void getVendorById() throws Exception {
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Michale");

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(vendor1));
        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        assertEquals("Michale", vendorDTO.getName());
    }

    @Test
    public void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("Michale");

        Vendor vendor1 = new Vendor();
        vendor1.setId(vendorDTO.getId());
        vendor1.setName(vendorDTO.getName());
        when(vendorRepository.save(any())).thenReturn(vendor1);

        VendorDTO saved = vendorService.createNewVendor(vendorDTO);
        assertEquals(vendorDTO.getId(), saved.getId());
        assertEquals(vendorDTO.getName(), saved.getName());
        assertEquals(VendorController.API_BASE_URL + "1", saved.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Jim");
        vendorDTO.setId(2L);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(2L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDto = vendorService.updateOrCreateVendor(vendorDTO.getId(), vendorDTO);
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(vendorDTO.getId(), savedDto.getId());
        assertEquals(VendorController.API_BASE_URL + "2", savedDto.getVendorUrl());
    }

    @Test
    public void deleteVendorById() throws Exception {
        vendorService.deleteVendorById(1L);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}
