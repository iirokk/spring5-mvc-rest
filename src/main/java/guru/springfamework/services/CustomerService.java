package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return persistCustomer(customerMapper.customerDtoToCustomer(customerDTO));
    }

    public CustomerDTO updateOrCreateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customerToPersist = customerMapper.customerDtoToCustomer(customerDTO);
        customerToPersist.setId(id);
        return persistCustomer(customerToPersist);
    }

    private CustomerDTO persistCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDto = customerMapper.customerToCustomerDTO(savedCustomer);
        return savedCustomerDto;
    }

    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        Customer patchedCustomer = customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }
            if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
            }
            return customerRepository.save(customer);
        }).orElseThrow(ResourceNotFoundException::new);
        return customerMapper.customerToCustomerDTO(patchedCustomer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
