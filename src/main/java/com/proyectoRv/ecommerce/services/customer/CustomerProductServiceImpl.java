package com.proyectoRv.ecommerce.services.customer;

import com.proyectoRv.ecommerce.dto.ProductDto;
import com.proyectoRv.ecommerce.entity.Product;
import com.proyectoRv.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String productName){
        List<Product> products = productRepository.findAllByNameContainingIgnoreCase(productName);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
