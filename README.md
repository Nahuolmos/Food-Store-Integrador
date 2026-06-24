# Food Store Integrador

Sistema de gestión para una tienda de comida desarrollado en Java, con persistencia en MySQL. Se trata de un proyecto integrador, por lo que la interfaz es de consola, sin frameworks ni interfaces gráficas de por medio.

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

- JDK 21 (es la versión configurada en el proyecto)
- MySQL Server
- Conector MySQL para Java (mysql-connector-j)
- NetBeans (los integrantes hemos usado este IDE para desarrollar el trabajo)

## Base de datos

Decidimos empezar y terminar la primera version del proyecto generado en memoria, basicamente decidimos trabajar con listas y arrays. Creamos y compilamos todo el codigo en base a una generacion en memoria para tener un primer vistazo de lo que seria el codigo terminado y asi revisar que fuese funcional. Una vez que logramos que el codigo corriera con la generacion en memoria, decidimos implementar la persistencia en base de datos. Se creo el paquete `config` y se agregó la clase `ConexionDB` para conectar a la base de datos del sql; se modificaron totalmente los DAO para que la persistencia estuviese correctamente aplicada, junto con eso, se creo una clase llamada `TestConexion` para como dice su nombre verificar la conexion fuese exitosa y asi poder saber que lo que hicimos correctamente.

El script correspondiente se encuentra en `src/config/schema.sql` y crea la base `pedidos_db` con las siguientes tablas:

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
private static final String PASSWORD = "112233654Facu";
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

- Modificación y listado de categorías.
- Modificación y listado de productos, con control de stock y categoría asociada.
- Registro y gestión de usuarios, con roles (ADMIN / USUARIO).
- Creación de pedidos con sus respectivos detalles, calculando el total de forma automática.
- Bajas lógicas: los registros no se eliminan físicamente, sino que se marcan como eliminados.
- Manejo de excepciones propias para errores de negocio, validación, duplicados y entidades no encontradas.

## Logica de negocio del sistema (Services)

Los services contienen la lógica de negocio del sistema, mientras que las DAO se limitan al acceso a datos. Cada entidad principal cuenta con su propio service (CategoriaService, ProductoService, UsuarioService, PedidoService), encargado de validar la información antes de persistirla, controlar el stock, verificar la inexistencia de duplicados (por ejemplo, un correo de usuario repetido) y calcular valores como el total de un pedido. Ante el incumplimiento de alguna regla de negocio, se lanzan las excepciones personalizadas correspondientes.
