INSERT INTO rol (nombre) VALUES ('ADMIN');
INSERT INTO rol (nombre) VALUES ('OPERARIO');
INSERT INTO rol (nombre) VALUES ('USUARIO');

INSERT INTO genero (nombre) VALUES ('Masculino');
INSERT INTO genero (nombre) VALUES ('Femenino');
INSERT INTO genero (nombre) VALUES ('Otro');

INSERT INTO usuario (nombre, apellido, email, clave, genero_id) VALUES ('Gino', 'Canestrari', 'canestrari@duck.com', '$2y$10$8EGu/gTv.tmnVD1n.vmFouw556CZcKD9xXHiRE9SJcrkpSNUtAVT6', '1');

INSERT INTO rol_de_usuario (usuario_id, rol_id) VALUES ('1', '1');