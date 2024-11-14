# 📚 LiterAlura!
¡Bienvenido a **LiterAlura**! 🎉
Esta aplicación en **Java Spring** te permite interactuar con la [API de Gutendex](https://gutendex.com/) para buscar libros por título, autor o idioma. Puedes guardar los libros en una base de datos local y realizar diferentes consultas para explorar tu colección. Todo desde la consola, de manera sencilla y eficiente.
---
## 🚀 Funcionalidades
- **Buscar libros**: Encuentra libros utilizando la API de Gutendex. Puedes buscar por:
  - Título
  - Autor
  - Idioma
- **Guardar libros**: Almacena los libros de interés en tu base de datos para acceder a ellos más tarde.
- **Consultas avanzadas**: Realiza consultas en la colección de libros guardados, incluyendo:
  - Listar todos los libros de un autor específico.
  - Filtrar libros por idioma.
  - Ver detalles de un libro en particular.
- **Mostrar información de autores**: Muestra los autores registrados y los libros asociados a cada uno.
---
## 🛠 Tecnologías Utilizadas
- **Java** + **Spring Boot**: Framework para el desarrollo de aplicaciones de Java.
- **JPA/Hibernate**: Gestión de persistencia para interactuar con la base de datos.
- **Gutendex API**: Fuente de datos de libros de dominio público.
- **Postgres Database (o cualquier base de datos relacional)**: Base de datos en memoria (o persistente) para almacenar la colección de libros.
---
# Cambiar URL de Gutendex API
gutendex.api.url=https://gutendex.com/
---
# Configuración de base de datos H2 (puedes cambiar a MySQL, PostgreSQL, etc.)
- spring.datasource.url=jdbc:h2:mem:testdb
- spring.datasource.driverClassName=org.h2.Driver
- spring.datasource.username=username
- spring.datasource.password=password
