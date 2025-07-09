## ✅ Proyecto: Ecommerce Technology - Backend API (Spring Boot)
Desarrollado por **Camilo Montero**

---

### 📌 Descripción

API RESTful desarrollada con Spring Boot para gestionar productos, marcas, categorías e imágenes, con integración a Amazon S3 para almacenamiento. Este backend es parte de un sistema de ecommerce modular y escalable.

---

### 🚀 Características actuales

- ✅ CRUD para productos, marcas y categorías.
- 🖼 Subida y eliminación de imágenes a Amazon S3.
- 🔗 Asociación de imágenes según el tipo de entidad (`TipoEntidad`).
- ⚠️ Manejo de errores con `ResponseStatusException`.
- 🧱 Estructura limpia por capas (`Controller`, `Service`, `Repository`).
- 📦 Persistencia con Spring Data JPA + Hibernate.

---

### 📁 Tecnologías utilizadas

- ☕ Java 21
- 🌱 Spring Boot
- 📊 Spring Data JPA (Hibernate)
- 🗄 MySQL
- ☁️ Amazon S3 (AWS SDK)
- 🔐 Spring Security (planificado)
- 🧪 JUnit + Mockito (planificado)
- 🧾 Swagger / OpenAPI (planificado)

---

### ⚙️ Cómo ejecutar el proyecto localmente

1. **Clona el repositorio:**

```bash
git clone https://github.com/tuusuario/ecommerce-tecnology.git
```

2. **Ingresa al proyecto:**

```bash
cd ecommerce-tecnology
```

3. **Copia el archivo de configuración de ejemplo:**

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

4. **Edita `application.properties` con tus propias credenciales:**

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/mi_base
spring.datasource.username=mi_usuario
spring.datasource.password=mi_contraseña

# AWS S3
aws.accessKey=TU_ACCESS_KEY
aws.secretKey=TU_SECRET_KEY
aws.region=us-east-2
aws.bucketName=mi-bucket
```

5. **Ejecuta el backend:**

```bash
./mvnw spring-boot:run
```

O desde tu IDE (IntelliJ, VS Code, Eclipse).

---

### 🔜 Próximas mejoras

- 🔐 Agregar autenticación y autorización con JWT y Spring Security.
- 📦 Desacoplar entidades usando DTOs con MapStruct.
- 🧪 Crear pruebas unitarias con JUnit y Mockito.
- 🖥️ Construir un frontend en Angular o React para consumir la API.
- 🧾 Documentar los endpoints con Swagger UI.
- 🧹 Refactorizar controladores para mayor limpieza y separación de lógica.

---

### 📝 Licencia

Este proyecto está bajo la Licencia MIT. Puedes usarlo, modificarlo y compartirlo libremente con fines personales o académicos.

---

### 🤝 Contacto

Camilo Montero  
