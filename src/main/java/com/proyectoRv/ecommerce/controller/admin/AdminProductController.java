package com.proyectoRv.ecommerce.controller.admin;

import com.proyectoRv.ecommerce.dto.ProductDto;
import com.proyectoRv.ecommerce.services.admin.adminProduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto){
        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtoList = adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDtoList = adminProductService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtoList);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        boolean deleted = adminProductService.deleteProduct(productId);
        if (deleted) {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Producto eliminado");
            body.put("status", String.valueOf(HttpStatus.OK.value()));
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        return new ResponseEntity<>("El producto no existe", HttpStatus.NOT_FOUND);
    }

}
