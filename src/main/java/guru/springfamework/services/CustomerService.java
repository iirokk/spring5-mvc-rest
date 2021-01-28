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
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
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
        savedCustomerDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());
        return savedCustomerDto;
    }

    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return null;
    }

    public void deleteCustomerById(Long id) {
    }
}
