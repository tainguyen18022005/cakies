# Cakies - Modern French Patisserie 🥐

Chào mừng bạn đến với **Cakies**, một ứng dụng web Full-stack quản lý cửa hàng bánh ngọt mang phong cách Pháp hiện đại. Dự án được thiết kế với giao diện tối giản, sang trọng nhằm tôn vinh những chiếc bánh thủ công cao cấp.

## 🚀 Công nghệ sử dụng
- **Frontend**: Vue.js 3 (Composition API), Vite, Vanilla CSS (Thiết kế tuỳ chỉnh hoàn toàn, không dùng framework UI).
- **Backend**: Java 17, Spring Boot 3.2, Spring Data JPA.
- **Database**: Microsoft SQL Server.
- **Triển khai**: Docker & Docker Compose (Môi trường phát triển & thực thi đồng bộ hóa cao).

## 📋 Yêu cầu hệ thống (Prerequisites)
Để chạy dự án này trên máy cá nhân, bạn chỉ cần cài đặt 2 công cụ duy nhất:
1. [Git](https://git-scm.com/downloads) (để tải mã nguồn).
2. [Docker Desktop](https://www.docker.com/products/docker-desktop/) (đã bao gồm Docker Compose).

---

## 🛠️ Hướng dẫn cài đặt & Chạy dự án (Setup & Run)

Chỉ với 3 bước đơn giản, bạn có thể khởi chạy toàn bộ hệ thống (Database, Backend, Frontend) mà không cần cài đặt thêm Node.js hay Java JDK trên máy tính.

### Bước 1: Clone dự án về máy
Mở Terminal / Command Prompt và chạy lệnh:
```bash
git clone https://github.com/tainguyen18022005/cakies.git
cd cakies
```

### Bước 2: Khởi chạy bằng Docker Compose
Hãy đảm bảo Docker Desktop của bạn đang mở và hoạt động. Tại thư mục gốc `cakies` (nơi chứa file `docker-compose.yml`), hãy chạy lệnh:
```bash
docker-compose up -d --build
```
*Lưu ý: Trong lần chạy đầu tiên, Docker sẽ cần tải về các Images cần thiết (Node, Java, SQL Server, Nginx...) và biên dịch source code, quá trình này có thể mất từ 3-5 phút.*

### Bước 3: Trải nghiệm ứng dụng
Khi lệnh trên chạy xong và các container đã báo trạng thái `Started`, bạn có thể truy cập các thành phần của hệ thống qua trình duyệt:

- 🎨 **Frontend (Giao diện người dùng)**: [http://localhost:5173](http://localhost:5173)
- ⚙️ **Backend API**: [http://localhost:8080](http://localhost:8080)
- 🗄️ **Database (SQL Server)**: Chạy trên cổng `1433` (Username: `sa` | Mật khẩu: `Cakies@12345`)

---

## 🧩 Cấu trúc Dự án
- `/cakies-frontend`: Chứa toàn bộ mã nguồn giao diện Vue.js. Được đóng gói bằng Nginx.
- `/cakies-backend`: Chứa mã nguồn Spring Boot. Database sẽ tự động được tạo và chèn dữ liệu mẫu (Sample data) ngay khi Backend khởi động thành công.
- `docker-compose.yml`: File cấu hình chung giúp liên kết Database, Backend và Frontend với nhau.

## 🛑 Dừng dự án
Để tắt toàn bộ hệ thống và giải phóng tài nguyên máy tính, bạn hãy chạy lệnh:
```bash
docker-compose down
```
*(Dữ liệu của Database vẫn sẽ được lưu trữ an toàn trong Docker Volume cho các lần chạy sau)*.

## 🤝 Đóng góp
Dự án được xây dựng với mục đích học tập và chia sẻ. Mọi đóng góp, báo lỗi (Issues) hoặc Pull Requests đều được chào đón!
