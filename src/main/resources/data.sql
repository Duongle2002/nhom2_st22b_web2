-- Dữ liệu mẫu cho bảng Member
INSERT INTO member (name, email, phone, address) VALUES 
('Nguyễn Văn A', 'nguyenvana@email.com', '0123456789', 'Hà Nội'),
('Trần Thị B', 'tranthib@email.com', '0987654321', 'TP. Hồ Chí Minh'),
('Lê Văn C', 'levanc@email.com', '0369852147', 'Đà Nẵng'),
('Phạm Thị D', 'phamthid@email.com', '0587412369', 'Cần Thơ'),
('Hoàng Văn E', 'hoangvane@email.com', '0741258963', 'Hải Phòng');

-- Dữ liệu mẫu cho bảng Company
INSERT INTO companies (name, code, description, address, phone, email) VALUES 
('Công ty TNHH ABC', 'ABC001', 'Công ty công nghệ thông tin', '123 Đường ABC, Quận 1, TP.HCM', '02812345678', 'info@abc.com.vn'),
('Tập đoàn XYZ', 'XYZ002', 'Tập đoàn đa ngành nghề', '456 Đường XYZ, Quận 3, TP.HCM', '02887654321', 'contact@xyz.com.vn'),
('Công ty DEF', 'DEF003', 'Công ty thương mại dịch vụ', '789 Đường DEF, Quận 5, TP.HCM', '02811223344', 'info@def.com.vn'),
('Công ty GHI', 'GHI004', 'Công ty sản xuất', '321 Đường GHI, Quận 7, TP.HCM', '02855667788', 'contact@ghi.com.vn'),
('Công ty JKL', 'JKL005', 'Công ty tài chính', '654 Đường JKL, Quận 10, TP.HCM', '02899887766', 'info@jkl.com.vn');

-- Dữ liệu mẫu cho bảng User (với password mã hóa BCrypt)
-- Tất cả mật khẩu: 123456
INSERT INTO users (username, password, full_name, email, phone, address, role, company_id) VALUES 
('admin', '$2a$10$DLcXTvfZ8s4khDHLE6vxhu6oKlfJt5Cp2iViDhHhn3xOr2wNMlN2u', 'Nguyễn Văn Admin', 'admin@system.com', '0901234567', 'Hà Nội', 'ADMIN', 1),
('manager', '$2a$10$DLcXTvfZ8s4khDHLE6vxhu6oKlfJt5Cp2iViDhHhn3xOr2wNMlN2u', 'Trần Thị Manager', 'manager@system.com', '0909876543', 'TP.HCM', 'MANAGER', 1),
('employee', '$2a$10$J/c6qTw.klZ97EorkLRzWuk3wmqsfA5SH11u9SvdncyU/VKVV1Jm6', 'Lê Văn Employee', 'employee@system.com', '0905555666', 'Đà Nẵng', 'EMPLOYEE', 1),
('guest', '$2a$10$w8Bbv9CMrbYxGah/D0.RVu8LkcU.sF63Wc3HbPK.WdGdDX94/IE8i', 'Phạm Thị Guest', 'guest@system.com', '0904444555', 'Cần Thơ', 'GUEST', 2);
