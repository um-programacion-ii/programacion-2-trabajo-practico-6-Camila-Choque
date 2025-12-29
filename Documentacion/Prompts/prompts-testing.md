## Prompt 1: Creación de tests unitarios para servicios (basados en test guía del profesor)

**Contexto:** El profesor proporcionó un test base como referencia inicial. A partir de este ejemplo se definió el patrón a seguir para el resto de los tests unitarios del proyecto.

### Prompt Utilizado:

> "Mi profesor nos dio un test unitario base como ejemplo (ProductoBusinessServiceTest con JUnit 5 y Mockito). Necesito que, basándote en ese ejemplo, me ayudes a crear tests unitarios similares para otros services del proyecto, manteniendo la misma estructura Arrange / Act / Assert, el uso de @Mock, @InjectMocks, Mockito y validaciones con assert y verify."

### Respuesta Recibida:

Se generó una clase de test con:

* `@ExtendWith(MockitoExtension.class)`
* Uso de `@Mock` para el repository
* Uso de `@InjectMocks` para el service
* Tests con `when` / `thenReturn`
* Verificaciones con `assertEquals` y `verify`

### Modificaciones Realizadas:

* Se adaptaron los nombres de métodos y clases al dominio real del proyecto.
* Se eliminaron impresiones por consola.
* Se agregaron asserts más específicos.

**Motivo:** ajustar el test a las reglas del dominio y a las convenciones del proyecto.

### Explicación del Prompt:

Este prompt se utilizó para garantizar que la lógica de negocio del service funcione correctamente de forma aislada, sin depender de la base de datos ni de otros componentes.

### Aprendizajes Obtenidos:

* Importancia del aislamiento en tests unitarios.
* Uso correcto de Mockito para simular dependencias.
* Validación de comportamiento y no solo de resultados.

---

## Prompt 2: Configuración de testing con base de datos H2

### Prompt Utilizado:

> "Necesito que me ayudes a configurar el entorno de testing para usar una base de datos en memoria H2 en Spring Boot."

### Respuesta Recibida:

Se configuró:

* `application-test.yml`
* Dependencia H2
* Propiedad `spring.jpa.hibernate.ddl-auto=create-drop`

### Modificaciones Realizadas:

* Se ajustaron perfiles activos.
* Se alineó el esquema con el entorno productivo.

### Explicación del Prompt:

Este prompt permitió ejecutar tests de integración sin afectar la base de datos real.

### Aprendizajes Obtenidos:

* Uso de perfiles en Spring.
* Ventajas de bases de datos en memoria.
* Separación de entornos.

