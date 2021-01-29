package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorDtoToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("test");

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        assertEquals(Long.valueOf(1L), vendor.getId());
        assertEquals("test", vendor.getName());
    }

    @Test
    public void vendorToVendorDto() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("test");

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
        assertEquals(Long.valueOf(1L), vendorDTO.getId());
        assertEquals("test", vendorDTO.getName());
        assertEquals(VendorController.API_BASE_URL + "1", vendorDTO.getVendorUrl());
    }
}