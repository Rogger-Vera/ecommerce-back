package com.proyectoRv.ecommerce.services.admin.category;

import com.proyectoRv.ecommerce.dto.CategoryDto;
import com.proyectoRv.ecommerce.entity.Category;
import com.proyectoRv.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category = Category.builder()
                .name(categoryDto.getName())
                .description((categoryDto.getDescription()))
                .build();

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
