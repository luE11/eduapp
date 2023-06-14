# eduap backend

Aplicación backend desarrollada con Spring boot, exponiendo servicios para la creación, consulta parametrizada, modificación y autenticación de usuarios por medio de JWT (Json Web Tokens).

### Herramientas empleadas

| Herramienta | Versión |
| ------------- | ------------- |
| Java (Openjdk)  | 17.0.7  |
| Spring boot  | 3.0.6  |
| Mariadb  | 10.4.28  |
| Linux Mint  | 21.1 Vera |

Commandos

- Empaquetar aplicación
```
- ./mvnw package
```
- Ejecutar servidor
```
- java -jar ./target/eduapp-0.0.1-SNAPSHOT.jar
```

Al ejecutar el servidor, se puede consultar la documentación de los servicios expuestos por medio de la herramienta Swagger UI al acceder a la url: 

http://localhost:8080/swagger-ui/index.html
