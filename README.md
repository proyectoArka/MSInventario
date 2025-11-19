
<div align="center">
	<h1>MSInventario</h1>
	<img src="https://img.shields.io/badge/SpringBoot-3.5.5-green" />
	<img src="https://img.shields.io/badge/Java-21-blue" />
	<img src="https://img.shields.io/badge/PostgreSQL-Database-blue" />
</div>

---


## ℹ️ Información sobre el Microservicio (MS)
El microservicio **MSInventario** es una pieza clave dentro del ecosistema de Arka, encargado de la gestión eficiente de productos y categorías, así como el control de stock en tiempo real. 

### Objetivos principales:
- Centralizar la administración de inventario.
- Permitir la integración con otros servicios mediante APIs REST.
- Facilitar la trazabilidad y auditoría de movimientos de productos.
- Mejorar la escalabilidad y mantenibilidad del sistema.

### Ventajas de usar MSInventario:
- Desacoplamiento de la lógica de negocio respecto a la infraestructura.
- Facilidad para agregar nuevas funcionalidades sin afectar el núcleo del sistema.
- Integración sencilla con herramientas de monitoreo, seguridad y configuración centralizada.
- Documentación automática y pruebas integradas.

## 🗂️ Descripción
Microservicio de inventario para el proyecto **Arka**. Permite gestionar productos, categorías y el stock, con APIs RESTful y documentación Swagger/OpenAPI.


## 🏗️ Arquitectura utilizada
Este microservicio sigue una arquitectura **hexagonal (Ports & Adapters)**, también conocida como **Clean Architecture**. Esta arquitectura promueve la separación de responsabilidades y facilita la escalabilidad, mantenibilidad y testeo del sistema.

### Características principales:
- **Dominio central:** La lógica de negocio y los modelos están desacoplados de frameworks y tecnologías externas.
- **Adaptadores:** Los controladores REST, repositorios JPA y otros componentes externos interactúan con el dominio a través de interfaces (puertos).
- **Inversión de dependencias:** El dominio no depende de la infraestructura, sino al revés.
- **Facilidad para pruebas:** Permite realizar pruebas unitarias y de integración de manera sencilla.

Esta arquitectura permite que el microservicio evolucione fácilmente, integrando nuevas tecnologías o cambiando las existentes sin afectar la lógica de negocio.

## 🚀 Tecnologías
- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- PostgreSQL
- Eureka Client (descubrimiento de servicios)
- Swagger/OpenAPI

## 🌐 Endpoints Principales
### Productos
- `GET /api/v1/productos` - Listar productos
- `POST /api/v1/productos` - Crear producto
- `PUT /api/v1/productos/{id}` - Actualizar producto
- `DELETE /api/v1/productos/{id}` - Baja lógica (isDelete)

### Categorías
- `GET /api/v1/categoria` - Listar categorías
- `POST /api/v1/categoria` - Crear categoría
- `PUT /api/v1/categoria/{id}` - Actualizar categoría
- `DELETE /api/v1/categoria/{id}` - Eliminar categoría

## 🛠️ Ejecución Rápida
```bash
./gradlew bootRun
```
Accede a la documentación interactiva en [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui/index.html?urls.primaryName=MS+Inventario+%288080%29)


## ⚙️ Configuración
Configura el microservicio en `application.yml` y `bootstrap.yml`.

## 🧩 Integración
Se integra con Eureka para descubrimiento de servicios y con Spring Cloud Config para configuración centralizada.

---