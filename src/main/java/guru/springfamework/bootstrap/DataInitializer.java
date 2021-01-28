package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        saveNewCategory("Fruits");
        saveNewCategory("Dried");
        saveNewCategory("Exotic");
        saveNewCategory("Nuts");
        saveNewCategory("Fresh");
        System.out.println("Data loaded. Categories: " + categoryRepository.count());
    }

    private void saveNewCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }
}
