
# Prompts para IA - Proyecto PaymentOrder

## 1. Generación del contrato a partir del WSDL y el SD PaymentOrder
Genera un contrato OpenAPI para un servicio de PaymentOrder basado en el WSDL adjunto y el Service Domain PaymentOrder de BIAN.
- Asegúrate de mapear todos los request y response existentes.
- Incluye estados y flujos de pago comunes según BIAN.
- Genera esquemas correctos para cada DTO siguiendo buenas prácticas de OpenAPI.
- Evita duplicar campos y usa nombres claros y consistentes.

## 2. Corrección de esquemas basados en request/response de Postman
Corrige los esquemas del contrato OpenAPI generado anteriormente usando los ejemplos de request y response del archivo Postman Collection.
- Mantén consistencia con los tipos de datos esperados.
- Asegúrate que todos los campos requeridos estén presentes.
- Aplica validaciones básicas de tipos y formatos (string, date-time, integer, etc.).
- Revisa que coincidan con los endpoints reales del servicio.

## 3. Definición de estados y flujos de pago según BIAN
Agrega los estados y flujos de pago al contrato y a los DTOs siguiendo el Service Domain PaymentOrder de BIAN.
- Asocia cada estado con los posibles eventos o transiciones de flujo.
- Asegúrate que los estados sean consistentes en todos los endpoints y DTOs.

## 4. Optimización de código y actualización de objetos en pruebas unitarias
Revisa el código de los servicios y mappers generados para PaymentOrder y optimízalo:
- Actualiza el acceso a los atributos de los objetos según buenas prácticas.
- Reemplaza código obsoleto o repetitivo.
- Mejora la legibilidad de los mappers y servicios.
- Genera ejemplos de pruebas unitarias que validen correctamente los atributos y estados de los objetos.

## 5. Mapeo de datos para transformar entidades a DTOs y viceversa
Genera mappers usando MapStruct para transformar entidades de PaymentOrder a DTOs y viceversa:
- Asegúrate de mapear todos los campos relevantes.
- Ignora campos que no deban persistir.
- Incluye lógica para transformar fechas y estados correctamente.
- Mantén la consistencia con el contrato OpenAPI generado.

## 6. Implementación y ayuda con test unitarios
Crea pruebas unitarias usando JUnit y Mockito para los servicios de PaymentOrder:
- Verifica la creación, actualización y consulta de órdenes de pago.
- Valida que los mappers transformen correctamente entidades a DTOs y viceversa.
- Asegúrate de cubrir escenarios de éxito y error.
- Usa mocks para dependencias externas como base de datos y servicios externos.

## 7. Scripts generados para la creación de tablas en PostgreSQL
Genera scripts SQL para crear tablas en PostgreSQL basadas en las entidades de PaymentOrder:
- Incluye todos los campos con sus tipos correctos.
- Define llaves primarias y relaciones si aplica.
- Añade constraints básicas (NOT NULL, UNIQUE) según el modelo.
- Los scripts deben ser ejecutables en un contenedor Docker PostgreSQL.

## 8. Resolución de Smell code y análisis con Snyk
Te paso un issue detectado por SonarQube o Snyk. Dame:
1. Qué tipo de problema es.
2. Riesgos si no se corrige.
3. Solución concreta.
4. Código corregido listo para usar (antes y después).
Solo responde según el issue que te doy, sin analizar el proyecto completo.

## 9. Conexiones a la BD desplegada con Docker
Tengo una conexión a PostgreSQL desplegada con Docker usando R2DBC en Spring Boot, pero da errores al conectar. 
- Explícame posibles causas del error.
- Sugiere soluciones concretas para que la conexión funcione.
- Indica ajustes en configuración, Docker o Spring Boot si aplica.
- No me des ejemplos genéricos, enfócate en resolver el problema real.
