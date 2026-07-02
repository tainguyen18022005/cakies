-- Dữ liệu mẫu - chỉ insert nếu chưa tồn tại (idempotent)

IF NOT EXISTS (SELECT 1 FROM category WHERE id = 1)
BEGIN
    SET IDENTITY_INSERT category ON;
    INSERT INTO category (id, name, description) VALUES (1, N'Bánh Sinh Nhật', N'Các loại bánh kem sinh nhật truyền thống và hiện đại');
    INSERT INTO category (id, name, description) VALUES (2, N'Bánh Cưới', N'Bánh kem nhiều tầng sang trọng cho tiệc cưới');
    INSERT INTO category (id, name, description) VALUES (3, N'Bánh Cupcake', N'Bánh cupcake nhỏ xinh phù hợp cho mọi dịp');
    SET IDENTITY_INSERT category OFF;
END;

IF NOT EXISTS (SELECT 1 FROM product WHERE id = 1)
BEGIN
    SET IDENTITY_INSERT product ON;
    INSERT INTO product (id, name, description, price, image_url, stock_quantity, category_id) VALUES
    (1, N'Bánh Kem Dâu Tây', N'Bánh kem sữa tươi trang trí dâu tây tươi mát', 350000, 'https://placehold.co/400x300/FFB6C1/fff?text=Dâu+Tây', 10, 1),
    (2, N'Bánh Chocolate Truffle', N'Bánh chocolate đậm vị kết hợp cacao nguyên chất', 420000, 'https://placehold.co/400x300/4a2c2a/fff?text=Chocolate', 15, 1),
    (3, N'Bánh Matcha Trà Xanh', N'Bánh bông lan trà xanh thanh mát, ít ngọt', 380000, 'https://placehold.co/400x300/7CAE7A/fff?text=Matcha', 8, 1),
    (4, N'Bánh Tiramisu Truyền Thống', N'Bánh Tiramisu chuẩn vị Ý thơm hương cà phê', 450000, 'https://placehold.co/400x300/c9a96e/fff?text=Tiramisu', 12, 1),
    (5, N'Bánh Cưới Hoa Hồng Trắng', N'Bánh 3 tầng trang trí hoa hồng kem bơ cao cấp', 2500000, 'https://placehold.co/400x300/f8f0e3/333?text=Hoa+Hồng', 2, 2),
    (6, N'Bánh Cưới Hoàng Gia', N'Bánh 5 tầng với họa tiết dát vàng lộng lẫy', 5000000, 'https://placehold.co/400x300/d4af37/fff?text=Hoàng+Gia', 1, 2),
    (7, N'Set 6 Cupcake Vani', N'Hộp 6 bánh cupcake vani kem phô mai', 150000, 'https://placehold.co/400x300/FFDAB9/333?text=Cupcake+Vani', 20, 3),
    (8, N'Set 12 Cupcake Mix Vị', N'Hộp 12 bánh cupcake đủ các vị dâu, chocolate, matcha', 280000, 'https://placehold.co/400x300/FFB347/fff?text=Cupcake+Mix', 15, 3);
    SET IDENTITY_INSERT product OFF;
END;
