package com.cakies.config;

import com.cakies.entity.Category;
import com.cakies.entity.Product;
import com.cakies.repository.CategoryRepository;
import com.cakies.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (categoryRepository.count() > 0) {
            return; // Dữ liệu đã tồn tại, không seed lại
        }

        // Tạo Categories
        Category sinhNhat = categoryRepository.save(new Category(null, "Bánh Sinh Nhật", "Các loại bánh kem sinh nhật truyền thống và hiện đại"));
        Category cuoi = categoryRepository.save(new Category(null, "Bánh Cưới", "Bánh kem nhiều tầng sang trọng cho tiệc cưới"));
        Category cupcake = categoryRepository.save(new Category(null, "Bánh Cupcake", "Bánh cupcake nhỏ xinh phù hợp cho mọi dịp"));

        // Tạo Products
        productRepository.save(new Product(null, "Bánh Kem Dâu Tây",
                "Bánh kem sữa tươi trang trí dâu tây tươi mát",
                new BigDecimal("350000"),
                "https://placehold.co/400x300/FFB6C1/fff?text=Dau+Tay",
                10, sinhNhat));

        productRepository.save(new Product(null, "Bánh Chocolate Truffle",
                "Bánh chocolate đậm vị kết hợp cacao nguyên chất",
                new BigDecimal("420000"),
                "https://placehold.co/400x300/4a2c2a/fff?text=Chocolate",
                15, sinhNhat));

        productRepository.save(new Product(null, "Bánh Matcha Trà Xanh",
                "Bánh bông lan trà xanh thanh mát, ít ngọt",
                new BigDecimal("380000"),
                "https://placehold.co/400x300/7CAE7A/fff?text=Matcha",
                8, sinhNhat));

        productRepository.save(new Product(null, "Bánh Tiramisu Truyền Thống",
                "Bánh Tiramisu chuẩn vị Ý thơm hương cà phê",
                new BigDecimal("450000"),
                "https://placehold.co/400x300/c9a96e/fff?text=Tiramisu",
                12, sinhNhat));

        productRepository.save(new Product(null, "Bánh Cưới Hoa Hồng Trắng",
                "Bánh 3 tầng trang trí hoa hồng kem bơ cao cấp",
                new BigDecimal("2500000"),
                "https://placehold.co/400x300/f8f0e3/333?text=Hoa+Hong",
                2, cuoi));

        productRepository.save(new Product(null, "Bánh Cưới Hoàng Gia",
                "Bánh 5 tầng với họa tiết dát vàng lộng lẫy",
                new BigDecimal("5000000"),
                "https://placehold.co/400x300/d4af37/fff?text=Hoang+Gia",
                1, cuoi));

        productRepository.save(new Product(null, "Set 6 Cupcake Vani",
                "Hộp 6 bánh cupcake vani kem phô mai",
                new BigDecimal("150000"),
                "https://placehold.co/400x300/FFDAB9/333?text=Cupcake+Vani",
                20, cupcake));

        productRepository.save(new Product(null, "Set 12 Cupcake Mix Vị",
                "Hộp 12 bánh cupcake đủ các vị dâu, chocolate, matcha",
                new BigDecimal("280000"),
                "https://placehold.co/400x300/FFB347/fff?text=Cupcake+Mix",
                15, cupcake));

        System.out.println(">>> [DataInitializer] Đã seed 3 categories và 8 products thành công!");
    }
}
