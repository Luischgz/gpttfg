package org.vaadin.example;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.web.client.RestTemplate;

@Route("")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setI18n(createSpanishI18n());

        loginForm.addLoginListener(event -> {
            String correo = event.getUsername();
            String contrasena = event.getPassword();

            Usuario usuario = new Usuario(correo, contrasena);

            try {
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.postForObject(
                        "http://localhost:8080/api/auth/login",
                        usuario,
                        String.class
                );

                if (response != null && response.contains("Verifica el MFA")) {
                    // Guardamos el correo para MFA
                    SessionData.setCorreo(correo);
                    SessionData.setMfaCompletado(false);

                    // Extraemos el rol del mensaje: "Login correcto. Rol: admin"
                    if (response.contains("Rol:")) {
                        String rol = response.split("Rol:")[1].trim();
                        SessionData.setRol(rol); // Guardamos el rol
                    }

                    getUI().ifPresent(ui -> ui.navigate("mfa"));
                } else {
                    loginForm.setError(true);
                    Notification.show(response); // Ej: "Credenciales incorrectas"
                }

            } catch (Exception e) {
                loginForm.setError(true);
                Notification.show("Error de conexión con el servidor");
            }
        });

        add(
                loginForm,
                new RouterLink("¿No tienes cuenta? Regístrate aquí", RegisterView.class),
                new RouterLink("¿Has olvidado tu contraseña?", ForgotPasswordView.class)
        );
    }

    private LoginI18n createSpanishI18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Inicio de sesión");
        i18n.getHeader().setDescription("Por favor, introduce tus credenciales");

        i18n.getForm().setUsername("Correo electrónico");
        i18n.getForm().setTitle("Iniciar sesión");
        i18n.getForm().setPassword("Contraseña");
        i18n.getForm().setSubmit("Entrar");
        i18n.getForm().setForgotPassword(null);

        i18n.getErrorMessage().setTitle("Credenciales no válidas");
        i18n.getErrorMessage().setMessage("Revisa tu correo y contraseña e inténtalo de nuevo.");

        return i18n;
    }
}
