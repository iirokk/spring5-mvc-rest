package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDTO> categoriesDTO = categoryService.getAllCategories();
        assertEquals(1, categoriesDTO.size());
        verify(categoryRepository).findAll();
    }

    @Test
    public void getCategoriesByName() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test");
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName("test");
        assertEquals("test", categoryDTO.getName());
        assertEquals(Long.valueOf(1L), categoryDTO.getId());

    }
}