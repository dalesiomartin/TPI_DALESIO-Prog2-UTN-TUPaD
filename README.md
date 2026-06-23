# Food Store – Sistema de Gestión de Pedidos de Comida

Trabajo Práctico Integrador – Programación 2
Tecnicatura Universitaria en Programación a Distancia (UTN)

Aplicación de consola en Java que gestiona Categorías, Productos, Usuarios y Pedidos de un negocio de comidas, utilizando Programación Orientada a Objetos, Colecciones en memoria, Interfaces y Excepciones propias.

## Integrante

- DALESIO, Gerardo Martin – Legajo 101869 - Grupo52

## Requisitos

- **JDK 21** o superior instalado.
- No requiere base de datos: toda la información se almacena en memoria durante la ejecución.
- No requiere dependencias externas ni librerías adicionales.

## Estructura del proyecto

```
TPI_DALESIO-Prog2-UTN-TUPaD/
└── src/
    ├── entities/      (Base, Categoria, Producto, Usuario, Pedido, DetallePedido)
    ├── enums/         (Estado, FormaPago, Rol)
    ├── interfaces/    (Calculable)
    ├── exceptions/    (EntidadNoEncontradaException, StockInvalidoException)
    ├── main/          (Main)
    ├── menu/          (MenuPrincipal, CategoriaMenu, ProductoMenu, UsuarioMenu, PedidoMenu)
    └── services/      (CategoriaServicio, ProductoServicio, UsuarioServicio, PedidoServicio, Validaciones)
```

## Cómo ejecutar el proyecto

### Opción A – Desde la línea de comandos

1. Clonar el repositorio:
   ```
   git clone [URL_DEL_REPOSITORIO]
   cd foodstore
   ```

2. Compilar todas las clases (desde la carpeta que contiene `src/`):
   ```
   javac -d bin $(find src -name "*.java")
   ```
   En Windows (PowerShell), si no se dispone de `find`:
   ```
   javac -d bin (Get-ChildItem -Recurse -Filter *.java -Path src).FullName
   ```

3. Ejecutar el programa:
   ```
   java -cp bin integrado.prog2.Main
   ```

### Opción B – Desde un IDE (NetBeans / IntelliJ / Eclipse)

1. Abrir el proyecto como "Java Project" existente, apuntando a la carpeta `src/`.
2. Verificar que el JDK configurado sea la versión 21 o superior.
3. Ejecutar la clase `main.Main`.

## Uso del sistema

Al iniciar, se muestra el menú principal:

```
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorías
2. Productos
3. Usuarios
4. Pedidos
0. Salir
Seleccione:
```

Cada opción abre un submenú con las operaciones CRUD correspondientes (Listar, Crear, Editar, Eliminar). Las eliminaciones son siempre lógicas (soft delete): el registro deja de listarse pero no se borra de la colección, para conservar el historial.

**Flujo sugerido para probar el sistema:**

1. Crear al menos una Categoría.
2. Crear uno o más Productos asociados a esa categoría.
3. Crear un Usuario.
4. Crear un Pedido: seleccionar el usuario, la forma de pago, y cargar uno o más detalles (producto + cantidad). El total se calcula automáticamente al confirmar el pedido.

## Decisiones de diseño relevantes

- **Generación de IDs:** cada entidad (`Categoria`, `Producto`, `Usuario`, `Pedido`, `DetallePedido`) recibe su id desde el Service correspondiente, que mantiene un contador propio de instancia (no estático). Las entidades nunca generan su propio id, evitando estado compartido global.
- **Excepciones propias:** `EntidadNoEncontradaException` y `StockInvalidoException` extienden `RuntimeException` (unchecked), por decisión de diseño para simplificar la propagación de errores entre capas sin forzar `throws` en cada método intermedio. Ambas se capturan explícitamente en la capa de menú para informar al usuario.
- **Dependencia circular Categoría–Producto:** `ProductoServicio` necesita a `CategoriaServicio` para validar la categoría al crear/editar un producto, y `CategoriaServicio` necesita a `ProductoServicio` para verificar si una categoría tiene productos activos antes de eliminarla. Se resuelve instanciando primero `CategoriaServicio`, luego `ProductoServicio` (que lo recibe por constructor), y finalmente conectando la referencia inversa con `categoriaServicio.setProductoServicio(...)` desde `Main`.
- **Creación de Pedido en tres pasos:** `iniciarPedido` → `agregarDetalle` (uno o más) → `confirmarPedido`. El pedido no ingresa a la colección de pedidos hasta confirmarse; si falla la carga de un detalle (por ejemplo, stock insuficiente), se descarta sin dejar datos inconsistentes en memoria.
- **Baja lógica (soft delete):** todas las entidades heredan de `Base`, que incluye el atributo `eliminado`. Ningún método `eliminar()` remueve físicamente un objeto de su colección.
- **Validación Genérica de Colecciones (Principio DRY):** Para evitar la duplicación de estructuras condicionales de control en la capa de presentación (`Menu`), se implementó el método genérico unificado `esListaVacia(List<? extends Base> lista)` dentro de la clase `Validaciones`. Aprovechando el polimorfismo y la arquitectura de herencia del proyecto (donde todas las entidades extienden de `Base`), este método procesa de forma segura colecciones de cualquier tipo de entidad, interceptando listas vacías antes de iniciar operaciones de escritura en la consola y garantizando una alta cohesión en todo el sistema.

## Documentación y video

- **Documento PDF (informe académico):** ver archivo en la raíz del repositorio
- **Video demostrativo:** https://youtu.be/gC2IGcTm7vs

## Tecnologías utilizadas

- Java 21
- Colecciones del paquete `java.util` (ArrayList)
- Sin frameworks externos

## Licencia

Trabajo académico desarrollado para la cátedra de Programación 2 – UTN.
