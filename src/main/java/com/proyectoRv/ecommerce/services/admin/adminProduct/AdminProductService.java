package com.proyectoRv.ecommerce.services.admin.adminProduct;

import com.proyectoRv.ecommerce.dto.ProductDto;

import java.util.List;

public interface AdminProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByName(String productName);
    boolean deleteProduct(Long id);
}
