# Challenge Spring Boot API

Este proyecto es un desafío que implementa una API RESTful utilizando Spring Boot, diseñada para gestionar una colección de libros y autores. Permite la búsqueda de libros a través de una API externa y el almacenamiento de información relevante en una base de datos.

## Tabla de Contenidos

- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación](#instalación)
- [Uso](#uso)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Características

- Búsqueda de libros utilizando la API de Gutendex.
- Almacenamiento de autores y libros en una base de datos.
- Prevención de duplicados al guardar libros y autores.
- Manejo de errores a través de excepciones personalizadas.
- Menu de opciones con consultas con JPA.
- Estadisticasa con Summary Statistics.
  

## Tecnologías Utilizadas

- [Java](https://www.oracle.com/java/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Hibernate](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Maven](https://maven.apache.org/)
- [RESTful API](https://restfulapi.net/)

## Instalación

Para instalar y ejecutar el proyecto localmente, sigue estos pasos:

1. Clona el repositorio:

   ```bash
   gh repo clone zVersatility/ChallengeSpringBootApi
  
2. Navega al directorio del proyecto:

   ```bash
   cd ChallengeSpringBootApi

3. Asegúrate de tener PostgreSQL y Java IDEA | JDK instalados.
4. Configura la base de datos en el archivo application.properties.
5. Ejecuta la aplicación.


 ## Uso

 Menu de opciones :
  
 ![image](https://github.com/user-attachments/assets/31f35ca9-1657-4071-9c30-a40108b395f9)

* **Opcion 01** - *Buscar libro por nombre, se busca el nombre ingresado por consola y se consulta a la API, si se encuentra resultado se registra en la base de datos.*

  ![image](https://github.com/user-attachments/assets/783fa814-eaed-4167-86a1-dd8e545025d5)

* **Opcion 02** - *Listar libros registrados , hace consulta a la base de datos y muestra todos los libros registrados correctamente.*

  ![image](https://github.com/user-attachments/assets/11be6d33-d9d1-423e-975a-c0380be8f220)

* **Opcion 03** - *Listar autores registrados , hace consulta a la base de datos y muestra todos los autores registrados correctamente.*

  ![image](https://github.com/user-attachments/assets/ef0cb8eb-1269-4839-bd73-b1fd3b6a58e0)

* **Opcion 04** - *Listar autores vivos en un determinado año , recibe un año (ejemplo : 1800) ingresado por consola y hace consulta a la base de datos y muestra todos los autores vivos en ese año*

  ![image](https://github.com/user-attachments/assets/9f33aca3-4bdb-44f7-b818-c8ffd88b54c3)

* **Opcion 05** - *Listar libros por idioma , se ingresa un idioma por consola (ejemplo español: es) hace consulta a la base de datos y muestra todos los libros registrados con el idioma español.*
* **Opcion 06** - *buscar autor por nombre , se ingresa un nombre y se hace consulta a la base de datos y muestra todos los autores que coincidan con el nombre ingresado.*
* **Opcion 07** - *Top 10 libros mas descargados , hace consulta a la base de datos y muestra  el top 10 de libros mas descargados en forma descendente*
* **Opcion 08** - *Estadisticas , usando Summary Statistics muestra el libro con el numero maximo y minimo de descargas.
  

 






 
