package com.cakies.service;

import com.cakies.entity.Category;
import com.cakies.entity.Product;
import com.cakies.repository.CategoryRepository;
import com.cakies.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<Product> getAllProducts(Long categoryId, Pageable pageable) {
        Objects.requireNonNull(pageable, "pageable cannot be null");
        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
