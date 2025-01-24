# Auth Services 🚀  
Es un servicio de autenticación desarrollado en Java utilizando **Spring Boot**. Implementa seguridad moderna con *JWT* y *Spring Security* para gestionar registros
y auntenticacion de Usuarios. Este proyecto está diseñado para ser la base de un sistema de autenticación escalable y con una seguridad basica.  

---

# Características Principales  
- Registro y autenticación de usuarios.  
- Generación y validación de *JSON Web Tokens (JWT)*.  
- Implementación de seguridad con *Spring Security*.  
- Arquitectura limpia y separación entre capas.  
- Gestión de base de datos relacional con *JPA* y *Hibernate*.  

---

# Tecnologías Backend Utilizadas  
- *Java 17*  
- *Spring Boot*  
- *Spring Security*  
- *JPA / Hibernate*  
- *JWT (JSON Web Tokens)*  
- *Maven*  

# Base de Datos  
- *MySql* (para pruebas y desarrollo).  

---

# Instalación y Configuración  
# Requisitos Previos  
- *JDK 17** o superior instalado.  
- *Maven 3.8** o superior instalado.  
- Opcional: Cliente para probar APIs (como Postman o cURL).  

# Pasos para Ejecutar el Proyecto  
1. Clona este repositorio:  
 - git clone https://github.com/MarioAraujoDEV/auth-services.git
2. Cambia el .properties al nombre y configuracion de tu BBDD (ejemplo usando MySQL)
  spring.datasource.url=jdbc:mysql://localhost:tu_puerto/tu_base_de_datos
  spring.datasource.username=tu_usuario
  spring.datasource.password=tu_contraseña
  spring.jpa.hibernate.ddl-auto=update
3. Construye el proyecto con Maven y lanzalo
 - mvn clean install
 - mvn spring-boot:run

# Prueba del servicio 
1. Abre  Postman o cURL y envia una petición POST a http://localhost:tu_puerto/api/auth/register
 - En tu body debe incluir un JSON del siguiente tipo:
  {
    "email": "ejemplo@gmail.com",  
    "username": "ejemplo",
    "name": "ejemplo",
    "lastname": "ejemplo",
    "password": "abc123.A@"
  }
 - Tiene validaciones por lo que en caso de que se introduzca algun campo mal ser recibira el campo y su mensaje en la respuesta que falla
2. Para iniciar sesión hacemos una petición a  http://localhost:tu_puerto/api/auth/login
 - Y en el body mandamos un usuario que ya hayamos creado en nuestra BBDD
   {
    "email": "ejemplo@gmail.com",
    "password": "abc123.A@"
   }
  - En caso de que todo vaya bien nos devolvera el JWT 
