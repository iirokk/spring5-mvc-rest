package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(1, customerDTOS.size());
        verify(customerRepository).findAll();
    }

    @Test
    public void getCustomerById() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals("Michale", customerDTO.getFirstname());
    }

    @Test
    public void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstname("Michale");
        customerDTO.setLastname("Weston");

        Customer customer1 = new Customer();
        customer1.setId(customerDTO.getId());
        customer1.setFirstname(customerDTO.getFirstname());
        customer1.setLastname(customerDTO.getLastname());
        when(customerRepository.save(any())).thenReturn(customer1);

        CustomerDTO saved = customerService.createNewCustomer(customerDTO);
        assertEquals(customerDTO.getId(), saved.getId());
        assertEquals(customerDTO.getFirstname(), saved.getFirstname());
        assertEquals(customerDTO.getLastname(), saved.getLastname());
        assertEquals("/api/v1/customer/1", saved.getCustomerUrl());
    }
}