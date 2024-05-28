package com.proyectoRv.ecommerce.services.admin.category;

import com.proyectoRv.ecommerce.dto.CategoryDto;
import com.proyectoRv.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();

}
