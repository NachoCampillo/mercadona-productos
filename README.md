Este proyecto es una API REST para la gestión de productos desarrollada con Spring Boot. Permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre productos identificados por su código EAN.

Se implementan características clave como autenticación JWT, caché in-memory con Caffeine, manejo de errores centralizado y validación de datos. El proyecto se ejecuta en la plataforma Replit, y las simulaciones de solicitudes HTTP se han probado con ReqBin.
Características Implementadas

1. Autenticación con JWT
La API está protegida con autenticación JWT (JSON Web Token). Solo los usuarios autenticados pueden realizar solicitudes a los endpoints protegidos.
Generación y validación de tokens JWT.
Uso de JwtRequestFilter para interceptar y validar las solicitudes HTTP.
Pruebas realizadas con ReqBin para verificar el comportamiento del token de autenticación.

2. Operaciones CRUD de Productos
GET /api/productos/{ean}: Obtiene el detalle del producto por su código EAN.
POST /api/productos: Crea un nuevo producto. Los campos del producto se validan utilizando anotaciones como @NotBlank y @Size.
PUT /api/productos: Actualiza un producto existente.
DELETE /api/productos/{id}: Elimina un producto por su ID.
GET /api/productos/all: Retorna una lista de todos los productos.

3. Caché In-Memory (Caffeine)
Se implementa Caffeine para mejorar el rendimiento del sistema, almacenando temporalmente los productos en memoria y evitando consultas repetidas a la base de datos.

@Cacheable: Almacena los productos en caché cuando se solicitan por su EAN.
@CacheEvict: Invalida la caché cuando se crea, actualiza o elimina un producto.

4. Base de Datos en Memoria (H2)
Se utiliza H2 como base de datos en memoria para almacenamiento temporal, facilitando la persistencia de productos durante las pruebas y simulaciones.

5. Manejo Global de Excepciones
Se centraliza el manejo de excepciones en la clase GlobalExceptionHandler, devolviendo respuestas consistentes y útiles:

EntityNotFoundException: Cuando un producto no se encuentra.
IllegalArgumentException: Para validaciones de datos de entrada.
MethodArgumentNotValidException: Manejo de errores de validación de campos.

6. Validaciones de Campos
Las validaciones se realizan en el lado del servidor utilizando Bean Validation:
Validación de campos como EAN antes de persistir los productos.

8. Simulaciones de Solicitudes HTTP
Para probar la API, se utilizó ReqBin como herramienta de simulación de solicitudes HTTP, capturando las respuestas para verificar el correcto funcionamiento de los endpoints protegidos y CRUD.

Instalación:

Clonar el repositorio:
Copiar código
git clone https://github.com/MercadonaITSeleccion/Ignacio_Campillo.git
cd mercadona

Compilar y ejecutar el proyecto:
mvn clean install
mvn spring-boot:run


Acceso a la base de datos H2: Una vez el proyecto esté corriendo, puedes acceder a la consola H2 en:
http://localhost:8080/h2-console

Uso de la API
1. Generación del Token JWT
   POST /authenticate: Enviar credenciales de usuario para obtener el token JWT
   {
  "username": "admin",
  "password": "password"
}
Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

2. Ejemplo de CRUD (Uso en ReqBin)
   Post para crear producto: /api/productos
   {
    "ean": "8437008459059"
}
Response (201 Created):
{
  "id": 1,
  "ean": "8437008459059",
  "codigoProducto": "84590",
  "proveedor": "Hacendado",
  "destino": "Tiendas Mercadona España"
}

Get para obtener producto por EAN GET /api/productos/{ean}
GET /api/productos/8437008459059 Response (200 OK):
{
  "id": 1,
  "ean": "8437008459059",
  "codigoProducto": "84590",
  "proveedor": "Hacendado",
  "destino": "Tiendas Mercadona España"
}

Actualizar Producto (PUT /api/productos/{id})
Debes proporcionar el ID del producto en la URL. Request Body:
{
  "ean": "8437008459059"
}
Response (200 OK):
{
  "id": 1,
  "ean": "8437008459059",
  "codigoProducto": "84590",
  "proveedor": "Hacendado",
  "destino": "Tiendas Mercadona España"
}

Eliminar Producto (DELETE /api/productos/{id})
DELETE /api/productos/1 Response (200 OK):

Obtener Todos los Productos (GET /api/productos/all)
Response (200 OK):
[
  {
    "id": 1,
    "ean": "8437008459059",
    "codigoProducto": "84590",
    "proveedor": "Hacendado",
    "destino": "Tiendas Mercadona España"
  },
  {
    "id": 2,
    "ean": "8437008459066",
    "codigoProducto": "84590",
    "proveedor": "Hacendado",
    "destino": "Tiendas Mercadona Portugal"
  }
]
Autenticación JWT
Es necesario incluir el token JWT en el encabezado de las solicitudes para las rutas protegidas. El formato del encabezado debe ser:
Authorization: Bearer <tu_token_jwt>
