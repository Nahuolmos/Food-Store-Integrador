# Food Store Integrador

Sistema de gestión para una tienda de comida (estilo hamburguesería) desarrollado en Java puro, con persistencia en MySQL. Se trata de un proyecto integrador, por lo que la interfaz es de consola, sin frameworks ni interfaces gráficas de por medio.

Permite administrar categorías, productos, usuarios y pedidos, aplicando las reglas de negocio correspondientes (control de stock, validaciones, bajas lógicas, entre otras).

## Estructura del proyecto

El código se organiza en capas:

- **entities**: clases del modelo (Usuario, Producto, Categoria, Pedido, DetallePedido).
- **DAO**: acceso a datos y consultas SQL contra MySQL.
- **service**: lógica de negocio y validaciones.
- **UI**: menús de consola para la interacción con el sistema.
- **enums**: Rol, Estado y FormaPago.
- **exceptions**: excepciones personalizadas (negocio, validación, entidad no encontrada, duplicados).
- **config**: configuración de la conexión a la base de datos.

## Requisitos

- JDK 25 (o la versión configurada en el proyecto)
- MySQL Server
- Conector MySQL para Java (mysql-connector-j)
- NetBeans (el proyecto está configurado para este IDE, aunque también puede compilarse con Ant o javac)

## Base de datos

Antes de ejecutar el proyecto es necesario crear la base de datos. El script correspondiente se encuentra en `src/config/schema.sql` y crea la base `pedidos_db` con las siguientes tablas:

- categoria
- producto
- usuario
- pedido
- detalle_pedido

Para ejecutarlo:

```sql
mysql -u root -p < src/config/schema.sql
```

El script incluye además la inserción de un usuario administrador de prueba.

### Configuración de la conexión

En `src/config/ConexionDB.java` se encuentran definidos la URL, el usuario y la contraseña de la base de datos. Estos valores deben ajustarse según el entorno correspondiente:

```java
private static final String URL = "jdbc:mysql://localhost:3306/pedidos_db";
private static final String USER = "root";
private static final String PASSWORD = "su_contraseña";
```

## Ejecución

1. Clonar o descargar el repositorio.
2. Abrir el proyecto en NetBeans (o importarlo en el IDE que se prefiera).
3. Agregar el conector de MySQL a las librerías del proyecto.
4. Crear la base de datos mediante el script `schema.sql`.
5. Configurar los datos de conexión en `ConexionDB.java`.
6. Ejecutar la clase `Main.java`.

Al iniciar, el sistema carga algunas categorías, productos y usuarios de ejemplo, con el fin de no comenzar con la base de datos vacía, y posteriormente presenta el menú principal.

## Funcionalidades

- Alta, baja, modificación y listado de categorías.
- Alta, baja, modificación y listado de productos, con control de stock y categoría asociada.
- Registro y gestión de usuarios, con roles (ADMIN / USUARIO).
- Creación de pedidos con sus respectivos detalles, calculando el total de forma automática.
- Bajas lógicas: los registros no se eliminan físicamente, sino que se marcan como eliminados.
- Manejo de excepciones propias para errores de negocio, validación, duplicados y entidades no encontradas.

## Sobre los services

Los services contienen la lógica de negocio del sistema, mientras que las DAO se limitan al acceso a datos. Cada entidad principal cuenta con su propio service (CategoriaService, ProductoService, UsuarioService, PedidoService), encargado de validar la información antes de persistirla, controlar el stock, verificar la inexistencia de duplicados (por ejemplo, un correo de usuario repetido) y calcular valores como el total de un pedido. Ante el incumplimiento de alguna regla de negocio, se lanzan las excepciones personalizadas correspondientes.

## Notas

Este proyecto fue desarrollado como trabajo integrador, con el objetivo de poner en práctica conceptos de programación orientada a objetos, organización en capas y persistencia mediante JDBC puro, sin la utilización de ORMs ni frameworks externos.
