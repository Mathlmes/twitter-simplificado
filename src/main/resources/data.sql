INSERT INTO tb_role (id, name) VALUES (1, 'admin') ON CONFLICT (id) DO NOTHING;
INSERT INTO tb_role (id, name) VALUES (2, 'basic') ON CONFLICT (id) DO NOTHING;