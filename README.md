# Sistema de Gestión de Cooperativas y Socios

Este proyecto es una aplicación de escritorio desarrollada en Java usando **Swing** como interfaz gráfica. Permite gestionar cooperativas, socios, tasas, datos personales y usuarios. Está conectado a una base de datos MySQL para el almacenamiento persistente de la información.

## Funcionalidades principales

- Gestión de **Cooperativas** (incluye nombre, sigla, dirección, contacto, usuario asociado, logo).
- Gestión de **Socios** (identificación, dirección, fecha de afiliación, cooperativa y datos personales asociados).
- CRUD completo (Crear, Leer, Actualizar, Eliminar).
- Interfaz gráfica amigable con **FlatLaf**.
- Manejo de fechas con **JCalendar**.
- Inserción de imágenes (logos) como BLOB en base de datos.
- Selección de datos relacionados mediante `JComboBox` mostrando el nombre pero guardando el código.

## Tecnologías y Librerías Usadas

- **Java SE** (JDK 11+)
- **Swing** (Interfaz gráfica)
- **MySQL** (Base de datos relacional)
- **JDBC** (Conexión a base de datos)

### Librerías externas (`lib/`):

| Librería                | Función                                                    |
|------------------------|-------------------------------------------------------------|
| `flatlaf-3.6.jar`       | Look & Feel moderno para interfaces Swing                  |
| `jcalendar-1.4.jar`     | Selección de fechas mediante un calendario (`JDateChooser`)|
| `mysql-connector-j-9.2.0.jar` | Driver JDBC para conexión con base de datos MySQL         |

## Estructura del Proyecto

src/
├── controller/ # Controladores de lógica de negocio
├── database/ # Conexión JDBC
├── ui/
│ ├── usuario/ # Paneles de usuario
│ ├── socio/ # Paneles de socio
│ ├── cooperativa/ # Paneles de cooperativa
│ └── ... # Otros componentes gráficos
└── AppMainForm.java # Ventana principal del sistema
lib/
└── *.jar # Librerías externas necesarias

## Requisitos para ejecutar

- **JDK 11 o superior**
- **MySQL Server instalado** y una base de datos configurada con las tablas necesarias.
- Las librerías `.jar` deben estar añadidas al classpath del proyecto.

## Cómo ejecutar

1. Clona o descarga este repositorio.
2. Asegúrate de que los `.jar` del directorio `lib/` estén en el classpath de tu IDE.
3. Ajusta la configuración de conexión en `ConexionJDBC.java`.
4. Ejecuta `AppMainForm` como aplicación Java.

---

**Desarrollado con fines educativos y de gestión local.**
# Sistema de Gestión de Cooperativas y Socios

Este proyecto es una aplicación de escritorio desarrollada en Java usando **Swing** como interfaz gráfica. Permite gestionar cooperativas, socios, tasas, datos personales y usuarios. Está conectado a una base de datos MySQL para el almacenamiento persistente de la información.

## Funcionalidades principales

- Gestión de **Cooperativas** (nombre, sigla, dirección, contacto, logo, usuario asociado).
- Gestión de **Socios** (identificación, dirección, fecha de afiliación, cooperativa y datos personales asociados).
- CRUD completo: Crear, Leer, Actualizar, Eliminar.
- Interfaz gráfica moderna con **FlatLaf**.
- Manejo de fechas mediante **JCalendar**.
- Inserción y visualización de imágenes (logos) como BLOB.
- Asociación de datos entre entidades mediante combo boxes (`JComboBox`).

## Tecnologías y Librerías Usadas

- **Java SE** (JDK 11+)
- **Swing** (GUI)
- **MySQL** (Base de datos relacional)
- **JDBC** (Interacción con la base de datos)

### Librerías externas (`lib/`):

| Librería                      | Función                                                  |
|------------------------------|-----------------------------------------------------------|
| `flatlaf-3.6.jar`            | Look & Feel moderno para Swing                           |
| `jcalendar-1.4.jar`          | Calendario `JDateChooser` para selección de fechas       |
| `mysql-connector-j-9.2.0.jar`| Driver JDBC para conectar a base de datos MySQL          |

## Esquema de Base de Datos

A continuación, algunos fragmentos SQL para crear las tablas principales:

```sql
CREATE TABLE usuario (
    UsuCod INT AUTO_INCREMENT PRIMARY KEY,
    UsuIde VARCHAR(20),
    UsuUsu VARCHAR(45),
    UsuPas VARCHAR(45),
    UsuEmp VARCHAR(45),
    UsuRol VARCHAR(45),
    UsuEst BOOLEAN
);

CREATE TABLE cooperativa (
    CooCod INT AUTO_INCREMENT PRIMARY KEY,
    CooIde VARCHAR(45),
    CooNom VARCHAR(45),
    CooSig VARCHAR(45),
    CooDir VARCHAR(45),
    CooTel INT,
    CooCor VARCHAR(45),
    CooSlo VARCHAR(45),
    CooLog BLOB,
    CooUsu VARCHAR(45)
);

CREATE TABLE socio (
    SocCod INT AUTO_INCREMENT PRIMARY KEY,
    SocIden VARCHAR(20),
    SocCor VARCHAR(45),
    SocTipPro VARCHAR(45),
    SocCta VARCHAR(45),
    SocDep VARCHAR(45),
    SocPro VARCHAR(45),
    SocDis VARCHAR(45),
    SocEmp INT,
    SocDat INT,
    SocFecha DATE,
    SocEst BOOLEAN
);
