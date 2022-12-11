# Read Me First

The following was discovered as part of building this project:

* The JVM level was changed from '11' to '17', review
  the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range)
  on the wiki for more details.

# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#data.sql.jpa-and-spring-data)

# Development Environment

Pasos para poder iniciar la plataforma.

* Instalar Docker engine y Docker compose.
* Ejecutar el comando:
    * docker compose -f initialConfig.yml up --build -d
    * Ejecutar la plataforma en nuestro editor de código favorito.
      La plataforma está configurada para trabajar en ambiente de desarrollo, razón por la cual la base de datos es borrada cada vez que se reinicia el servidor de pruebas.
    * El Script que contiene los datos de pruebas se encuentra en ./src/main/resources/import.sql, estos datos se registraran automáticamente cada vez que se inicie el servidor en ambiente de pruebas.
* Para obtener el token de acceso del usuario administrador y poder hacer uso de la plataforma debemos usar las siguientes credenciales:
    * User: seleccionKruger, password: seleccionKruger2022
    * User: rgavilanesKruger, password: rgavilanesKruger2022
* EL servicio para obtener el token es:
    * http://localhost:8080/oauth/token
        * Para poder consumirlo debemos usar Basic Auth con las siguientes credenciales:
            * User: krugerVaccinationInventoryUser
            * password: patito123
        * El proceso se puede visualizar en la carpeta static del proyecto.
    * Podemos usar esta url para obtener un token para los distintos usuarios con sus roles respectivos.
* Usar el token en la interfaz Swagger.
    * http://localhost:8080/swagger-ui/index.html

Para ejecutar el proyecto en producción se debe compilar y generar un archivo .jar y la secuencia de ejecución sería algo similar a lo siguiente:
* Docker File:
    * ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /micro-service.jar  --spring.profiles.active=prod"]
    * Java Local
        * java -jar /micro-service.jar  --spring.profiles.active=prod
          Debemos recordar que el ambiente de producción únicamente genera el esquema de base de datos, más no persiste los datos de prueba, razón por la cual no vamos a tener datos en la base a menos que los ingresemos manualmente



