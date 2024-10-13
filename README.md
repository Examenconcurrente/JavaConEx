
# Examen Concurrente

[Repositorio](https://github.com/Examenconcurrente/JavaConEx.git)

Proyecto realizado por Jose Daniel Martín y Hugo Sanchez.

## JavaConEx

**JavaConEx** es una aplicación que implementa una arquitectura concurrente utilizando **Java Spring Boot** y **Server-Sent Events (SSE)** para transmitir datos en tiempo real. La aplicación permite cargar datos desde archivos CSV a una base de datos, utilizando hilos para procesar la información de manera eficiente y asegurando la concurrencia mediante **semáforos** y **CountDownLatch**. Además, ofrece una interfaz web para interactuar con estos datos, los cuales se visualizan en gráficos dinámicos.

Los datos cargados desde la base de datos también se transmiten en tiempo real a través de hilos, que los envían a la interfaz utilizando eventos **SSE**, permitiendo una visualización fluida y actualizada de los valores en la página web.

Este proyecto está diseñado para gestionar datos de diferentes distribuciones estadísticas, como los **datos normales**, **exponenciales** y de **T-Student**.


## Flujo de Funcionamiento de la Aplicación

1. **Inicio de la Aplicación**:
   - La aplicación se inicia ejecutando el método `main` en la clase `JavaConExApplication`.
   - Spring Boot se encarga de inicializar el contexto de la aplicación y de inyectar las dependencias necesarias.

2. **Carga de Datos CSV**:
   - Después de la inicialización, el método `init` anotado con `@PostConstruct` se ejecuta automáticamente.
   - Este método llama a `loadCSVService` para cargar datos desde varios archivos CSV (`valores_normales.csv`, `valores_t_student.csv`, `valores_exponenciales.csv`) a la base de datos.

3. **Apertura del Navegador**:
   - Una vez que los datos se han cargado, el método `openBrowser` se llama para abrir automáticamente la URL `http://localhost:8080/menu.html` en el navegador predeterminado del sistema operativo.

4. **Interacción con la Base de Datos**:
   - La aplicación proporciona varios servicios y controladores para interactuar con los datos cargados.
   - Por ejemplo, `ValorDataController` expone endpoints REST para obtener y guardar datos de la tabla `valor_data`.
   - Similarmente, `TStudentDataService` y `ExponentialDataService` manejan la lógica de negocio para las tablas `t_student_data` y `exponential_data`, respectivamente.

5. **Interfaz Web**:

    La interfaz web permite a los usuarios interactuar con los datos cargados en la base de datos.

   - La interfaz utiliza **D3.js** para visualizar los datos en gráficos dinámicos.
   - Los datos se transmiten en tiempo real desde el servidor al cliente utilizando **Server-Sent Events (SSE)**.
   - El servidor utiliza `SseEmitter` para enviar datos concurrentemente a través de hilos.
   - La interfaz web se actualiza automáticamente con los datos recibidos, proporcionando una experiencia interactiva y en tiempo real.

## Clases

### `DataController`

Este controlador maneja las solicitudes HTTP para transmitir datos de manera concurrente usando **Server-Sent Events (SSE)**. Contiene tres rutas:

- **`/hilos-concurrentes`**: Envía los datos de la entidad `ValorData` en tiempo real, utilizando el método `streamCSVData()` del servicio `LoadCSVService`.
- **`/exponential/stream`**: Envía datos exponenciales en tiempo real mediante el método `streamExponentialData()`.
- **`/tstudent/stream`**: Envía datos de T-Student en tiempo real utilizando el método `streamTStudentData()`.

Cada una de estas rutas utiliza un `ExecutorService` para manejar los datos de manera concurrente y garantizar que el servidor no se bloquee mientras envía los datos.

### `LoadCSVService`

Este servicio es responsable de cargar datos desde archivos CSV a la base de datos y transmitir esos datos en tiempo real mediante SSE. Utiliza un `ExecutorService` para manejar la concurrencia y un semáforo (`Semaphore`) para asegurar que solo un hilo a la vez acceda a recursos compartidos como la base de datos.

- **`loadCSVToDatabase`**, **`loadExponentialCSVToDatabase`**, y **`loadTStudentCSVToDatabase`**: Cargan datos desde archivos CSV a las tablas correspondientes en la base de datos.
- **`streamCSVData`**, **`streamExponentialData`**, y **`streamTStudentData`**: Transmiten los datos de las tablas `ValorData`, `ExponentialData`, y `TStudentData` en tiempo real utilizando SSE, asegurándose de que los datos se envíen de manera concurrente y no bloqueen el servidor.

### `ValorData`

Es la entidad que representa la tabla `valor_data` en la base de datos. Contiene dos campos:
- `id`: El identificador único para cada registro.
- `value`: El valor correspondiente al registro.

### `ValorDataController`

Este controlador ofrece dos rutas para interactuar con los datos almacenados en `valor_data`:

- **GET `/valores`**: Recupera todos los valores almacenados en la tabla.
- **POST `/valores`**: Guarda un nuevo valor en la base de datos.

### `ValorDataService`

Proporciona la lógica de negocio para manejar los datos de la entidad `ValorData`. Incluye métodos para:

- Recuperar todos los valores con `getAllValores()`.
- Guardar un nuevo valor en la base de datos con `saveValor()`.

### `ValorRepository`

Es una interfaz que extiende `JpaRepository`, proporcionando métodos para acceder y modificar los datos de `ValorData`. También incluye un método personalizado:

- **`truncateTable()`**: Trunca la tabla `valor_data`, eliminando todos los registros.

## Problemas y Soluciones

### Problema 1: Sincronización de la representación de las bolas

**Descripción:**
La velocidad de representación de las bolas está sujeta a la velocidad de la gravedad para que se vean más naturales. Inicialmente, agregamos el `sleep` en los hilos, pero tras pruebas vimos que los hilos se generaban demasiado perfectos, leían todos los ID por orden y todo era demasiado perfecto.

**Solución:**
Decidimos agregar, en vez del `sleep` en los hilos, el `setTimeout` en la actualización de la información para que diese la sensación de que se genera a la vez de las bolas. Esto no es posible porque la información que sacan los hilos es casi instantánea y las bolas, por la gravedad mencionada, tardan en caer.

### Problema 2: Transmisión de datos de la base de datos al front

**Descripción:**
En un inicio, la transmisión de datos de la base de datos al front se realizaba por medio de hilos. Guardábamos esa información en un `Map` de una variable para llamarlo a nuestro HTML, pero este cargaba de golpe y, por consiguiente, carecía de concurrencia.

**Solución:**
Investigando, encontramos `SseEmitter` para poder transmitir los datos en tiempo real sin necesidad de guardarlos en una variable.