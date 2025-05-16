# MOPR Money Lover Frontend

Đây là phần frontend của dự án **MOPR Money Lover**, một ứng dụng hỗ trợ người dùng quản lý tài chính cá nhân hiệu quả và tiện lợi. Dự án được phát triển bởi sinh viên Đại học Sư phạm Kỹ thuật TP.HCM.

## 🧩 Công nghệ sử dụng

* **Java**
* **Gradle** (sử dụng `build.gradle.kts`, `settings.gradle.kts`)
* **JavaFX** (giao diện người dùng)
* **IntelliJ IDEA** (khuyến nghị để phát triển và chạy)

## 📁 Cấu trúc thư mục

```plaintext
├── .idea/                 # Cấu hình dự án cho IntelliJ IDEA
├── app/                   # Mã nguồn chính của ứng dụng frontend
├── gradle/                # Cấu hình Gradle wrapper
├── build.gradle.kts       # Tệp cấu hình build chính
├── settings.gradle.kts    # Cấu hình các module Gradle
├── gradlew, gradlew.bat   # Script Gradle wrapper (Linux/Windows)
├── .gitignore             # Danh sách các file bị Git bỏ qua
├── hs_err_pid*.log        # Log lỗi JVM (nếu có)
└── README.md              # Tệp hướng dẫn (bạn đang xem)
```

## 🔧 Các chức năng chính

### Đăng ký

Cho phép người dùng tạo tài khoản mới bằng cách nhập email, mật khẩu và các thông tin cá nhân.

### Đăng nhập

Người dùng đăng nhập vào hệ thống để sử dụng các tính năng quản lý tài chính.

### Quản lý ví

Tạo, chỉnh sửa, xoá ví và chọn ví hiện tại để quản lý giao dịch trong ví đó.

### Quản lý hóa đơn

Ghi chép các giao dịch thu/chi, chỉnh sửa, xoá và phân loại theo ví, danh mục, thời gian.

### Quản lý ngân sách

Thiết lập và theo dõi ngân sách cho từng danh mục trong khoảng thời gian xác định.

### Xem thống kê

Hiển thị biểu đồ và số liệu giúp người dùng nắm được tổng quan tình hình tài chính.

### Quản lý mục chi tiêu

Thêm, xoá hoặc sửa danh mục thu/chi và tuỳ chỉnh biểu tượng, màu sắc.

### Xem thông báo

Hiển thị các thông báo liên quan như vượt ngân sách, nhắc nhở, hoạt động gần đây...

### Nhắc nhở chi tiêu

Tạo các nhắc nhở định kỳ cho việc ghi chép chi tiêu, thanh toán định kỳ...

### Quản lý sự kiện chi tiêu

Tạo các sự kiện như cưới hỏi, du lịch, mua sắm và theo dõi giao dịch trong từng sự kiện.

## 🚀 Hướng dẫn chạy dự án

1. **Yêu cầu hệ thống**:

   * Java 11 trở lên
   * Gradle 7 trở lên
   * IntelliJ IDEA (hoặc IDE tương thích)

2. **Clone dự án**:

```bash
git clone https://github.com/UTEUniversity-Projects/MOPR-Money-Lover-Frontend.git
cd MOPR-Money-Lover-Frontend
git checkout main
```

3. **Build và chạy ứng dụng**:

```bash
./gradlew run
```

Hoặc mở dự án bằng IntelliJ và chạy trực tiếp từ IDE.

## 👥 Đóng góp

Dự án được phát triển bởi:

* [dopaminee (Lê Tấn Trụ)](https://github.com/dopaminee)
* [PhucLe0809 (Lê Hồng Phúc)](https://github.com/PhucLe0809)

Mọi đóng góp hoặc phản hồi vui lòng tạo issue hoặc pull request trên GitHub.

## 📄 Giấy phép

Hiện tại dự án chưa công bố giấy phép sử dụng. Vui lòng liên hệ với nhóm phát triển nếu bạn có nhu cầu sử dụng lại mã nguồn.
