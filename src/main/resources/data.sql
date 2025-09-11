INSERT INTO company (id, name, address) VALUES
                                            (1, 'Le Huu Duong', '123 Street A');

INSERT INTO role (id, name) VALUES
                                (1, 'USER'),
                                (2, 'ADMIN');

INSERT INTO user_demo (id, name, email, password, company_id) VALUES
    (1, 'Duong', '123@gmail.com', '$2a$10$wz77O7rJdVdxUtbzMC.cr.LeEuegtVpE6fd/VA7hSd8ukbk6AVdY6', 1),
    (2, 'Admin', 'duong2@example.com', '$2a$10$4liN9rIhuJHQVDfoWCG74eRDFn0eynxjzRGr6wkWzg4XGaublfg9.', 1);


INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),
                                              (2, 2);

-- Khởi động lại bộ đếm IDENTITY sau khi chèn thủ công (H2 không hỗ trợ subquery trong RESTART WITH)
ALTER TABLE company ALTER COLUMN id RESTART WITH 2;
ALTER TABLE role ALTER COLUMN id RESTART WITH 3;
ALTER TABLE user_demo ALTER COLUMN id RESTART WITH 3;
