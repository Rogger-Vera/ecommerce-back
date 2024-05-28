package com.proyectoRv.ecommerce.services.customer;

import com.proyectoRv.ecommerce.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByName(String productName);

}
