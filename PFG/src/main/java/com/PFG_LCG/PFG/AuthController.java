package com.PFG_LCG.PFG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    private final List<RegistroPendiente> registrosPendientes = new CopyOnWriteArrayList<>();
    private final List<RecuperacionPendiente> recuperacionesPendientes = new CopyOnWriteArrayList<>();
    private final List<MFAPendiente> mfaPendientes = new CopyOnWriteArrayList<>();


    @PostMapping("/login")
    public String login(@RequestBody Usuario loginUsuario) {
        System.out.println("Intento de login con: " + loginUsuario.getCorreo());
        System.out.println("Contraseña recibida: " + loginUsuario.getContrasena());

        Usuario usuario = usuarioRepository.findByCorreo(loginUsuario.getCorreo());

        if (usuario != null) {
            System.out.println("Hash guardado en BD: " + usuario.getContrasena());
            boolean match = encoder.matches(loginUsuario.getContrasena(), usuario.getContrasena());
            System.out.println("¿Coincide la contraseña?: " + match);

            if (match) {
                // Generar código MFA
                String codigo = generarCodigo();

                mfaPendientes.removeIf(p -> p.getCorreo().equals(usuario.getCorreo()));
                mfaPendientes.add(new MFAPendiente(usuario.getCorreo(), codigo));

                emailService.enviarCorreo(
                        usuario.getCorreo(),
                        "Código de verificación MFA",
                        "Tu código para completar el acceso es: " + codigo
                );

                return "Verifica el MFA. Rol:" + usuario.getRol();
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }

        return "Credenciales incorrectas.";
    }

    @PostMapping("/confirm-mfa")
    public String confirmarMfa(@RequestParam String correo, @RequestParam String codigo) {
        System.out.println("Verificando MFA para el correo: " + correo);
        System.out.println("Código MFA recibido: " + codigo);

        MFAPendiente match = mfaPendientes.stream()
                .filter(m -> m.getCorreo().equals(correo) && m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);

        if (match == null) {
            System.out.println("Código MFA inválido o no encontrado.");
            return "Código MFA inválido.";
        }

        System.out.println("Código MFA verificado correctamente.");
        mfaPendientes.remove(match);
        return "MFA correcto. Puedes continuar.";
    }
    
    @PostMapping("/start-register")
    public String startRegister(@RequestBody Usuario nuevoUsuario) {
        if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()) != null) {
            return "Este correo ya está registrado.";
        }

        String codigo = generarCodigo();
        String hashedPassword = encoder.encode(nuevoUsuario.getContrasena());

        RegistroPendiente pendiente = new RegistroPendiente(nuevoUsuario.getCorreo(), hashedPassword, codigo);
        registrosPendientes.add(pendiente);

        emailService.enviarCorreo(
                nuevoUsuario.getCorreo(),
                "Código de verificación",
                "Tu código para completar el registro es: " + codigo
        );

        return "Se ha enviado un código de verificación a tu correo.";
    }

    @PostMapping("/confirm-register")
    public String confirmRegister(@RequestParam String correo, @RequestParam String codigo) {
        RegistroPendiente pendiente = registrosPendientes.stream()
                .filter(r -> r.getCorreo().equals(correo) && r.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);

        if (pendiente == null) {
            return "Código inválido o expirado.";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(pendiente.getContrasenaHash());
        nuevoUsuario.setRol(Usuario.Rol.usuario);

        usuarioRepository.save(nuevoUsuario);
        registrosPendientes.remove(pendiente);

        emailService.enviarCorreo(
                correo,
                "Registro confirmado",
                "Tu cuenta ha sido registrada correctamente. Ya puedes iniciar sesión."
        );

        return "Cuenta creada con éxito. Ya puedes iniciar sesión.";
    }


    @PostMapping("/start-reset")
    public String iniciarReset(@RequestParam String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "No hay ninguna cuenta con ese correo.";
        }

        String codigo = generarCodigo();
        recuperacionesPendientes.removeIf(r -> r.getCorreo().equals(correo));
        recuperacionesPendientes.add(new RecuperacionPendiente(correo, codigo));

        emailService.enviarCorreo(
                correo,
                "Recuperación de contraseña",
                "Tu código para restablecer la contraseña es: " + codigo
        );

        return "Código de recuperación enviado al correo.";
    }

    @PostMapping("/confirm-reset")
    public String confirmarReset(
            @RequestParam String correo,
            @RequestParam String codigo,
            @RequestParam String nuevaContrasena) {

        RecuperacionPendiente match = recuperacionesPendientes.stream()
                .filter(r -> r.getCorreo().equals(correo) && r.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);

        if (match == null) {
            return "Código inválido o expirado.";
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            return "Usuario no encontrado.";
        }

        String nuevaHash = encoder.encode(nuevaContrasena);
        usuario.setContrasena(nuevaHash);
        usuarioRepository.save(usuario);
        recuperacionesPendientes.remove(match);

        emailService.enviarCorreo(
                correo,
                "Contraseña actualizada",
                "Tu contraseña ha sido restablecida correctamente."
        );

        return "Contraseña restablecida correctamente.";
    }

    private String generarCodigo() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
