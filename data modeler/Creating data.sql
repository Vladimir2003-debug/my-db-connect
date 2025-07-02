
INSERT INTO Dato (DatCod, DatApeMat, DatApePat, DatNom) VALUES
(1, 'Juan', 'Pérez', 'X'),
(2, 'Lucía', 'Gonzales', 'Y'),
(3, 'Carlos', 'Díaz', 'Z'),
(4, 'Ana', 'Torres', 'W');
INSERT INTO Rol (RolCod, RolNom, RolRol) VALUES 
(1, 'Administrador', 'Admin'),
(2, 'Gestor', 'Gest'),
(3, 'Cajero', 'Cash'),
(4, 'Supervisor', 'Sup');
INSERT INTO Cooperativa 
(CooIde, CooNom, CooSig, CooDir, CooTel, CooCor, CooSlo, CooLog, CooUsu)
VALUES
('IDE001', 'Coop Norte', 'CN', 'Av. Norte 111', 054111111, 'norte@coop.com', 'Tu norte financiero', 'norte.png', 'admin1'),
('IDE002', 'Coop Sur', 'CS', 'Av. Sur 222', 054222222, 'sur@coop.com', 'Impulsando el sur', 'sur.png', 'admin2'),
('IDE003', 'Coop Este', 'CE', 'Av. Este 333', 054333333, 'este@coop.com', 'Crece con nosotros', 'este.png', 'admin3'),
('IDE004', 'Coop Oeste', 'CO', 'Av. Oeste 444', 054444444, 'oeste@coop.com', 'Solidez y futuro', 'oeste.png', 'admin4');

INSERT INTO Usuario (UsuIde, UsuRol, UsuUsu, UsuEmp, UsuPas) VALUES
('12345678', 1, 'admin1', 1, 'hashpass_admin1'),
('23456789', 2, 'gestor1', 2, 'hashpass_gestor1'),
('34567890', 3, 'cajero1', 3, 'hashpass_cajero1'),
('45678901', 4, 'super1', 4, 'hashpass_super1');
INSERT INTO Persona (PerIden, PerCor, PerFot, PerCoo, PerDat, PerFecha) VALUES
('98765432', 'lucia@mail.com', 'lucia.jpg', 1, 1, '1990-05-20'),
('87654321', 'marco@mail.com', 'marco.jpg', 2, 2, '1985-03-15'),
('76543210', 'sandra@mail.com', 'sandra.jpg', 3, 3, '1992-07-12'),
('65432109', 'jose@mail.com', 'jose.jpg', 4, 4, '1988-11-30');
INSERT INTO Socio 
(SocIden, SocCor, SocTipPro, SocCta, SocDep, SocPro, SocDis, SocEmp, SocDat, SocFecha) VALUES
('CI001', 'lucia@mail.com', 'Natural', '123-111', 'Arequipa', 'Arequipa', 'Cercado', 1, 1, '2021-01-10'),
('CI002', 'marco@mail.com', 'Natural', '123-222', 'Arequipa', 'Camaná', 'Mariscal Cáceres', 2, 2, '2022-02-20'),
('CI003', 'sandra@mail.com', 'Natural', '123-333', 'Cusco', 'Cusco', 'Santiago', 3, 3, '2020-03-15'),
('CI004', 'jose@mail.com', 'Natural', '123-444', 'Puno', 'Puno', 'Juliaca', 4, 4, '2023-04-25');
INSERT INTO Producto (ProdDes, ProdIden, ProdMon, ProdSoc) VALUES
('Préstamo Rápido', 'P001', 'Soles', 1),
('Crédito Vehicular', 'P002', 'Soles', 2),
('Microcrédito', 'P003', 'Dólares', 3),
('Línea de Crédito', 'P004', 'Soles', 4);
INSERT INTO Tasa (TasIden, TasDes, TasTas, TasPlaz, TasIniFecha, TasFinFecha) VALUES
('TA001', 'Tasa estándar anual', 10.50, 'Anual', '2025-01-01', '2025-12-31'),
('TA002', 'Tasa promocional', 8.25, 'Semestral', '2025-01-01', '2025-06-30'),
('TA003', 'Tasa ajustable', 9.75, 'Trimestral', '2025-03-01', '2025-09-01'),
('TA004', 'Tasa especial cliente fiel', 7.50, 'Anual', '2025-01-01', '2025-12-31');

