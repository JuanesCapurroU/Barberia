# 🚀 **PROMPT ACTUALIZADO PARA ANDROID STUDIO - SISTEMA DE AUTENTICACIÓN BARBERÍA**

## 📋 **CONTEXTO DE LOS CAMBIOS REALIZADOS:**

He implementado un **sistema completo de autenticación** en el backend que **OBLIGA** a los usuarios a registrarse y loguearse antes de poder usar la aplicación. Esto prepara el sistema para servicios a domicilio.

### **🔧 CAMBIOS TÉCNICOS IMPLEMENTADOS:**

1. **✅ Tablas unificadas**: Se resolvió el conflicto entre tablas `cliente` y `clientes`
2. **✅ Sistema de autenticación JWT**: Tokens con expiración de 5 horas
3. **✅ Encriptación de contraseñas**: BCrypt implementado
4. **✅ Validaciones robustas**: Campos obligatorios y formatos válidos
5. **✅ Campo dirección agregado**: Para futuros servicios a domicilio
6. **✅ CORS configurado**: Para desarrollo y producción
7. **✅ Base de datos conectada**: Supabase funcionando correctamente

---

## 🎯 **TAREAS CRÍTICAS PARA ANDROID:**

### **1. CAMBIO DE FLUJO PRINCIPAL:**
```
❌ FLUJO ANTERIOR: App → Acceso Directo → Funcionalidades
✅ FLUJO NUEVO: App → Verificar Token → Login/Registro → Funcionalidades
```

### **2. PANTALLAS OBLIGATORIAS A CREAR:**

#### **🔐 Pantalla de Login (CRÍTICA)**
```kotlin
// Reemplaza completamente el acceso directo actual
class LoginActivity : AppCompatActivity() {
    // Campos requeridos:
    // - EditText correo (email)
    // - EditText contraseña
    // - Button login
    // - TextLink "¿No tienes cuenta? Regístrate"
}
```

#### **📝 Pantalla de Registro (NUEVA)**
```kotlin
class RegisterActivity : AppCompatActivity() {
    // Campos obligatorios:
    // - EditText nombre (mínimo 2 caracteres)
    // - EditText celular (10-15 caracteres)
    // - EditText correo (formato email válido)
    // - EditText contraseña (mínimo 6 caracteres)
    // - EditText confirmarContraseña
    // - EditText direccion (opcional)
    // - Button registrar
}
```

#### **👤 Pantalla de Perfil (NUEVA)**
```kotlin
class ProfileActivity : AppCompatActivity() {
    // Mostrar información del usuario logueado:
    // - Nombre, celular, correo, dirección
    // - Button "Editar Perfil"
    // - Button "Cerrar Sesión"
}
```

#### **🔄 Splash Screen con Verificación**
```kotlin
class SplashActivity : AppCompatActivity() {
    // Verificar si existe token válido
    // Si existe → Ir a MainActivity
    // Si no existe → Ir a LoginActivity
}
```

---

## 🔌 **INTEGRACIÓN CON BACKEND:**

### **3. SERVICIOS API ACTUALIZADOS:**

```kotlin
interface AuthService {
    
    // REGISTRO DE CLIENTE
    @POST("api/auth/cliente/registro")
    suspend fun registrarCliente(@Body cliente: ClienteRegistroDto): Response<AuthResponse>
    
    // LOGIN DE CLIENTE
    @POST("api/auth/cliente/login")
    suspend fun loginCliente(@Body login: ClienteLoginDto): Response<AuthResponse>
    
    // VERIFICAR TOKEN
    @POST("api/auth/cliente/verificar-token")
    suspend fun verificarToken(@Header("Authorization") token: String): Response<AuthResponse>
    
    // OBTENER PERFIL
    @GET("api/clientes/perfil")
    suspend fun obtenerPerfil(@Header("Authorization") token: String): Response<ClienteResponse>
    
    // LOGIN ADMIN (si es necesario)
    @POST("api/auth/admin/login")
    suspend fun loginAdmin(@Body login: AdminLoginDto): Response<AuthResponse>
}
```

### **4. MODELOS DE DATOS:**

```kotlin
// DTO para registro
data class ClienteRegistroDto(
    val nombre: String,
    val celular: String,
    val correo: String,
    val contraseña: String,
    val direccion: String? = null
)

// DTO para login
data class ClienteLoginDto(
    val correo: String,
    val contraseña: String
)

// Respuesta de autenticación
data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val cliente: ClienteResponse?
)

// Datos del cliente
data class ClienteResponse(
    val id: Long,
    val nombre: String,
    val celular: String,
    val correo: String,
    val direccion: String?,
    val role: String = "CLIENTE"
)
```

---

## 🔑 **GESTIÓN DE SESIÓN:**

### **5. SHAREDPREFERENCES PARA TOKEN:**
```kotlin
class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    
    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }
    
    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }
    
    fun saveUserData(cliente: ClienteResponse) {
        prefs.edit()
            .putLong("user_id", cliente.id)
            .putString("user_name", cliente.nombre)
            .putString("user_email", cliente.correo)
            .putString("user_address", cliente.direccion)
            .apply()
    }
    
    fun clearSession() {
        prefs.edit().clear().apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}
```

### **6. INTERCEPTOR PARA TOKENS:**
```kotlin
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = SessionManager.getToken()
        
        val newRequest = if (token != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }
        
        return chain.proceed(newRequest)
    }
}
```

---

## ⚠️ **CAMBIOS CRÍTICOS EN CÓDIGO EXISTENTE:**

### **7. ACTUALIZAR TODAS LAS LLAMADAS API:**
```kotlin
// ❌ ANTES (sin autenticación):
@GET("api/servicios")
suspend fun getServicios(): Response<List<Servicio>>

// ✅ AHORA (con autenticación):
@GET("api/servicios")
suspend fun getServicios(@Header("Authorization") token: String): Response<List<Servicio>>
```

### **8. MANEJO DE ERRORES ACTUALIZADO:**
```kotlin
when (response.code()) {
    401 -> {
        // Token expirado o inválido
        SessionManager.clearSession()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    400 -> {
        // Datos inválidos
        showError("Datos incorrectos")
    }
    409 -> {
        // Usuario ya existe
        showError("El correo ya está registrado")
    }
}
```

---

## 📱 **FLUJO DE NAVEGACIÓN ACTUALIZADO:**

### **9. ARQUITECTURA DE NAVEGACIÓN:**
```
SplashActivity
    ↓ (verificar token)
    ├─ Token válido → MainActivity
    └─ Sin token → LoginActivity
                        ↓
                    RegisterActivity
                        ↓ (registro exitoso)
                    MainActivity
```

### **10. MENÚ PRINCIPAL ACTUALIZADO:**
```kotlin
// Agregar al menú principal:
// - "Mi Perfil" (nueva opción)
// - "Cerrar Sesión" (nueva opción)
// - Mantener opciones existentes (Reservas, Servicios, etc.)
```

---

## 🔧 **CONFIGURACIÓN TÉCNICA:**

### **11. URL BASE ACTUALIZADA:**
```kotlin
const val BASE_URL = "http://tu-servidor:8080/"
```

### **12. VALIDACIONES FRONTEND:**
```kotlin
// Validaciones que DEBES implementar:
fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validatePassword(password: String): Boolean {
    return password.length >= 6
}

fun validatePhone(phone: String): Boolean {
    return phone.length in 10..15
}

fun validateName(name: String): Boolean {
    return name.length >= 2
}
```

---

## 🚨 **PRIORIDADES DE IMPLEMENTACIÓN:**

### **ALTA PRIORIDAD (IMPLEMENTAR PRIMERO):**
1. ✅ **SplashActivity** con verificación de token
2. ✅ **LoginActivity** funcional
3. ✅ **RegisterActivity** funcional
4. ✅ **SessionManager** para manejo de tokens
5. ✅ **AuthInterceptor** para headers automáticos

### **MEDIA PRIORIDAD:**
6. ✅ **ProfileActivity** para gestión de perfil
7. ✅ **Validaciones** de campos
8. ✅ **Manejo de errores** actualizado

### **BAJA PRIORIDAD:**
9. ✅ **UX mejoras** (animaciones, loading states)
10. ✅ **Persistencia** de datos offline

---

## 📋 **CHECKLIST DE IMPLEMENTACIÓN:**

- [ ] Crear SplashActivity con verificación de token
- [ ] Crear LoginActivity con campos correo/contraseña
- [ ] Crear RegisterActivity con todos los campos obligatorios
- [ ] Implementar SessionManager para tokens
- [ ] Crear AuthInterceptor para headers automáticos
- [ ] Actualizar todas las llamadas API existentes
- [ ] Implementar manejo de errores 401 (token expirado)
- [ ] Crear ProfileActivity para gestión de usuario
- [ ] Agregar opciones de perfil al menú principal
- [ ] Implementar validaciones de campos
- [ ] Probar flujo completo: Registro → Login → Uso de app

---

## 🎯 **DATOS PARA TESTING:**

```json
// Usuario de prueba para registrar:
{
  "nombre": "Usuario Test",
  "celular": "3001234567",
  "correo": "test@email.com",
  "contraseña": "test123",
  "direccion": "Calle Test #123"
}

// Luego usar para login:
{
  "correo": "test@email.com",
  "contraseña": "test123"
}
```

---

## ⚡ **RESPUESTAS DEL BACKEND:**

### **Registro Exitoso (201):**
```json
{
  "success": true,
  "message": "Cliente registrado exitosamente",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "cliente": {
    "id": 1,
    "nombre": "Usuario Test",
    "correo": "test@email.com",
    "role": "CLIENTE"
  }
}
```

### **Login Exitoso (200):**
```json
{
  "success": true,
  "message": "Login exitoso",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "cliente": {
    "id": 1,
    "nombre": "Usuario Test",
    "correo": "test@email.com",
    "direccion": "Calle Test #123",
    "role": "CLIENTE"
  }
}
```

---

## 🚀 **RESULTADO FINAL:**

Una vez implementado, tendrás:
- ✅ **Sistema de autenticación completo**
- ✅ **Usuarios obligados a registrarse**
- ✅ **Sesiones persistentes**
- ✅ **Preparado para servicios a domicilio**
- ✅ **Seguridad robusta**

**¡Implementa estos cambios y tu app estará lista para el sistema de autenticación completo!** 🎯
