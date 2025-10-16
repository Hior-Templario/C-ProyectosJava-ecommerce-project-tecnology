# EcommerceProjectTecnology

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 19.0.6.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Karma](https://karma-runner.github.io) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
🧭 PANEL ADMINISTRADOR – FLUJOS Y MÓDULOS PRINCIPALES
1. Inicio (Dashboard)
KPIs (cantidad de productos, categorías, clientes, ventas, stock bajo, etc.)

Últimos productos ingresados

Accesos rápidos a módulos clave

2. Gestión de Usuarios (empleados/admins del sistema)
Tabla con:

Nombre

Correo

Rol

Estado (activo/inactivo)

Botones: Editar, Desactivar, Ver perfil

Formulario al hacer clic en "Editar":

Nombre

Correo

Rol

Estado
✅ El administrador sí puede editar estos datos

3. Perfil de Usuario (área personal para autogestión)
Ver datos personales

Cambiar contraseña

Editar nombre y correo (opcional según política del sistema)

Foto de perfil (opcional)

4. Gestión de Productos
Tabla con:

Código

Nombre

Precio

Marca

Categoría

Stock actual

Botones: Editar, Eliminar, Ver

Formulario de registro/edición de producto:

Código

Nombre

Descripción

Precio

Marca (seleccionable)

Categoría (seleccionable)

Imagen

Stock inicial (opcional, puedes dejarlo en 0 y luego usar el módulo de inventario)

5. Gestión de Inventario (iStock o entradas y salidas de inventario)
Módulo separado de productos.

Opciones principales:

Entrada de productos (aumenta stock)

Salida de productos (por ventas, ajustes, devoluciones)

Formulario de entrada/salida:

Selección de producto

Cantidad

Tipo de movimiento (entrada/salida)

Motivo (opcional)

Fecha

📦 Esto permite llevar control real de las existencias.

6. Gestión de Categorías
Listado de categorías

Crear nueva categoría (nombre, descripción)

Editar / Eliminar

7. Gestión de Marcas
Listado de marcas

Crear nueva marca (nombre, descripción)

Editar / Eliminar

8. Clientes (usuarios externos del ecommerce o compradores)
Listado de clientes:

Nombre

Correo

Teléfono

Dirección (opcional)

Fecha de registro

Botones: Ver, Editar, Eliminar (si es necesario)

Formulario de cliente:

Nombre

Correo

Teléfono

Dirección

✅ Se puede usar también para registrar clientes manualmente desde el sistema, si aplica (por ejemplo, en ventas físicas o llamadas).

9. Ventas / Pedidos (opcional, si piensas agregarlo)
Fecha de venta

Cliente

Productos vendidos

Total

Estado del pedido (pendiente, pagado, enviado, cancelado)

🔧 Complementos posibles:
Módulo de reportes

Sistema de autenticación con roles (admin, empleado, vendedor, etc.)

Notificaciones de bajo stock

Filtros y búsqueda avanzada en tablas