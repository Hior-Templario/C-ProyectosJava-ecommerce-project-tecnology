# ✅ Proyecto: Ecommerce Technology

Desarrollado por **Camilo Montero**

---

## 📌 Descripción

Sistema completo de ecommerce modular, compuesto por una API REST en Spring Boot (backend), una interfaz web en Angular/TypeScript (frontend), y una base de datos MySQL. Incluye almacenamiento de imágenes en Amazon S3.

---

## 🚀 Características actuales

- ✅ CRUD para productos, marcas y categorías.
- 🖼 Subida y eliminación de imágenes a Amazon S3.
- 🔗 Asociación de imágenes según tipo de entidad (`TipoEntidad`).
- ⚠️ Manejo de errores con `ResponseStatusException`.
- 🔐 Autenticación y autorización con JWT + Spring Security.
- 📦 Uso de DTOs + MapStruct.
- 🧱 Backend con arquitectura por capas (`Controller`, `Service`, `Repository`).
- 🗄 Script SQL de base de datos incluido.
- 🖥️ Frontend desacoplado en Angular.


---

## 📁 Estructura del repositorio

```
ecommerce-tecnology/
├── backend/     → Proyecto Spring Boot (API)
├── frontend/    → Aplicación Angular en TypeScript
├── database/    → Scripts SQL de la base de datos
└── README.md    → Este archivo
```

---

## ⚙️ Cómo ejecutar el backend

1. Clona el repositorio:

```bash
git clone https://github.com/tuusuario/ecommerce-tecnology.git
cd ecommerce-tecnology/backend
```

2. Copia el archivo de configuración de ejemplo:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. Edita el archivo con tus credenciales:

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/mi_base
spring.datasource.username=mi_usuario
spring.datasource.password=mi_contraseña

# AWS S3
aws.accessKey=TU_ACCESS_KEY
aws.secretKey=TU_SECRET_KEY
aws.region=us-east-2
aws.bucketName=mi-bucket
```

4. Ejecuta:

```bash
./mvnw spring-boot:run
```

---

## ⚙️ Cómo ejecutar el frontend (Angular)

1. Entra a la carpeta:

```bash
cd ecommerce-tecnology/frontend
```

2. Instala las dependencias:

```bash
npm install
```

3. Ejecuta el servidor local:

```bash
ng serve
```

4. Abre en tu navegador: `http://localhost:4200`

---

## 🗃️ Base de datos

Los scripts `.sql` están en la carpeta `database/`. Puedes importarlos desde **MySQL Workbench**:

1. Abre Workbench → `File > Open SQL Script`
2. Ejecuta cada archivo `.sql` según el orden lógico (ej. primero `categoria.sql`, luego `producto.sql`, etc.)

---

## 🔜 Próximas mejoras

- 🧪 Pruebas unitarias con JUnit + Mockito.
- 🧾 Documentación Swagger.
- 🐳 Dockerizar backend y frontend.
- 🧹 Refactorización y modularización avanzada.

---

## 📁 Tecnologías utilizadas

- ☕ Java 21, Spring Boot, JPA, Hibernate
- 🗄 MySQL
- ☁️ Amazon S3 (AWS SDK)
- 💻 Angular 17, TypeScript
- 🧪 JUnit, Mockito (planeado)
- 🔐 Spring Security (planeado)

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT.

---

## 🤝 Contacto

Camilo Montero  
