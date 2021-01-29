package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    public DataInitializer(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        saveNewCategory("Fruits");
        saveNewCategory("Dried");
        saveNewCategory("Exotic");
        saveNewCategory("Nuts");
        saveNewCategory("Fresh");
        System.out.println("Data loaded. Categories: " + categoryRepository.count());

        saveNewCustomer("Ted", "Mossley");
        saveNewCustomer("Anthony", "Dennis");
        saveNewCustomer("Mary", "Ellis");
        System.out.println("Data loaded. Customers: " + customerRepository.count());

        saveNewVendor("Fruits Inc.");
        saveNewVendor("Tammy's Fruit Emporium");
        saveNewVendor("Fresh Foods Baltimore");
    }

    private void saveNewCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    private void saveNewCustomer(String firstname, String lastname) {
        Customer customer = new Customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customerRepository.save(customer);
    }

    private void saveNewVendor(String name) {
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendorRepository.save(vendor);
    }
}
