# Yuli Web — Instrucciones de uso

Web personal dedicada a Yuli. Proyecto Spring Boot con frontend HTML/CSS/JS integrado.

---

## Requisitos

- **Java 17 o superior** (verificar con `java -version`)
- **Maven 3.8+** (verificar con `mvn -version`)
- Conexión a internet para cargar las tipografías de Google Fonts (solo la primera vez)

---

## Cómo ejecutar

### Opción 1 — Con Maven instalado

```bash
# Desde la carpeta raíz del proyecto (donde está el pom.xml):
mvn spring-boot:run
```

### Opción 2 — Generar JAR ejecutable

```bash
mvn package -DskipTests
java -jar target/yuli-web-1.0.0.jar
```

Una vez arrancado, abrir en el navegador:

```
http://localhost:8080
```

Para detener la aplicación: `Ctrl + C`

---

## Estructura del proyecto

```
yuli-web/
├── src/
│   └── main/
│       ├── java/com/yuli/web/
│       │   └── YuliApplication.java        ← Clase principal (no modificar)
│       └── resources/
│           ├── static/
│           │   ├── css/style.css            ← Estilos (no modificar)
│           │   ├── js/main.js               ← JavaScript (no modificar)
│           │   └── images/                  ← ★ AQUÍ SE COLOCAN LAS IMÁGENES
│           │       ├── hero.jpg
│           │       ├── quien.jpg
│           │       ├── historia1.jpg
│           │       ├── historia2.jpg
│           │       ├── historia3.jpg
│           │       ├── momento1.jpg
│           │       ├── momento2.jpg
│           │       ├── momento3.jpg
│           │       ├── momento4.jpg
│           │       └── gallery/
│           │           ├── g001.jpg
│           │           ├── g002.jpg
│           │           └── ... hasta g100.jpg
│           ├── templates/
│           │   └── index.html               ← Plantilla HTML principal
│           └── application.properties
└── pom.xml
```

---

## Cómo sustituir imágenes

**Regla fundamental: no es necesario modificar ningún archivo de código.**
Solo hay que reemplazar los archivos `.jpg` con las imágenes reales.

### Imágenes de sección

| Archivo a reemplazar         | Dónde aparece                          | Proporción recomendada |
|------------------------------|----------------------------------------|------------------------|
| `images/hero.jpg`            | Portada principal (fondo completo)     | 16:9 horizontal        |
| `images/quien.jpg`           | Sección "Quién es Yuli"                | 3:4 vertical (retrato) |
| `images/historia1.jpg`       | Primera imagen de "Su historia"        | 4:3 horizontal         |
| `images/historia2.jpg`       | Segunda imagen de "Su historia"        | 4:3 horizontal         |
| `images/historia3.jpg`       | Tercera imagen de "Su historia"        | 4:3 horizontal         |
| `images/momento1.jpg`        | Tarjeta de momento 1                   | 4:3 horizontal         |
| `images/momento2.jpg`        | Tarjeta de momento 2                   | 4:3 horizontal         |
| `images/momento3.jpg`        | Tarjeta de momento 3                   | 4:3 horizontal         |
| `images/momento4.jpg`        | Tarjeta de momento 4                   | 4:3 horizontal         |

### Galería (100 imágenes)

Carpeta: `images/gallery/`
Nombres: `g001.jpg`, `g002.jpg`, ..., `g100.jpg`

- Sustituir cada archivo por la foto real con **el mismo nombre**
- Formato: JPEG (`.jpg`)
- Resolución mínima recomendada: **600 × 600 px**
- Las imágenes se muestran en cuadrícula cuadrada con `object-fit: cover`, por lo que cualquier proporción funciona sin deformar el layout

### Procedimiento

1. Copiar la imagen real a la carpeta correcta
2. Renombrarla con el nombre exacto que aparece en la tabla
3. Reiniciar la aplicación (si ya estaba corriendo: `Ctrl+C` y volver a ejecutar)
4. Refrescar el navegador

---

## Personalización de textos

Para cambiar los textos (nombre, subtítulo, carta, etc.), editar el archivo:

```
src/main/resources/templates/index.html
```

Los textos están escritos directamente en HTML y son fácilmente localizables.

---

## Notas técnicas

- Puerto por defecto: `8080` (modificable en `application.properties` → `server.port`)
- La galería incluye lightbox con navegación por teclado (← → Esc) y swipe táctil
- Diseño completamente responsive (móvil, tablet, escritorio)
- Las imágenes de galería se cargan de forma diferida (`loading="lazy"`) para mejor rendimiento
- No hay dependencias de terceros en el frontend (sin jQuery, sin Bootstrap)

---

## Solución de problemas frecuentes

**La app no arranca:**
- Verificar que Java 17+ está instalado: `java -version`
- Verificar que Maven está instalado: `mvn -version`
- Verificar que el puerto 8080 no está ocupado

**Las imágenes no aparecen:**
- Verificar que el nombre del archivo coincide exactamente (minúsculas, sin espacios)
- Verificar que la extensión es `.jpg`
- Reiniciar la aplicación tras añadir imágenes

**Las fuentes no cargan:**
- Verificar conexión a internet
- Las fuentes se cargan desde Google Fonts; sin conexión se usarán las fuentes del sistema
