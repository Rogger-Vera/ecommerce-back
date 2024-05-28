package com.proyectoRv.ecommerce.services.admin.adminProduct;

import com.proyectoRv.ecommerce.dto.ProductDto;
import com.proyectoRv.ecommerce.entity.Category;
import com.proyectoRv.ecommerce.entity.Image;
import com.proyectoRv.ecommerce.entity.Product;
import com.proyectoRv.ecommerce.repository.CategoryRepository;
import com.proyectoRv.ecommerce.repository.ImageRepository;
import com.proyectoRv.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    public ProductDto addProduct(ProductDto productDto){
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
        Product product = Product.builder()
                .name(productDto.getName())
                .description((productDto.getDescription()))
                .price(productDto.getPrice())
                .category(category)
                .build();

        if (productDto.getImgFiles() != null) {
            List<Image> imageList = new ArrayList<>();
            for (MultipartFile file : productDto.getImgFiles()) {
                try {
                    Image img = Image.builder()
                            .data(file.getBytes())
                            .product(product)
                            .build();
                    imageList.add(img);
                } catch (IOException e) {
                    // Manejar la excepción adecuadamente
                    throw new RuntimeException("Fallo el procesamiento de las imagenes", e);
                }
            }
            product.setImages(imageList); // Asociar las imágenes con el producto
        }

        product = productRepository.save(product);
        // Guardar las imágenes después de guardar el producto
//        for (Image image : imageList) {
//            imageRepository.save(image);
//        }

        return product.getDto();
    }

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String productName){
        List<Product> products = productRepository.findAllByNameContainingIgnoreCase(productName);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
