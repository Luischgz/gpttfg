package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.web.client.RestTemplate;

@Route("register")
public class RegisterView extends VerticalLayout {



    public RegisterView() {
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        H2 title = new H2("Registro de nuevo usuario");

        EmailField emailField = new EmailField("Correo electrónico");
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Correo inválido");
        emailField.setRequiredIndicatorVisible(true);

        PasswordField passwordField = new PasswordField("Contraseña");
        PasswordField repeatPasswordField = new PasswordField("Repetir contraseña");
        passwordField.setRequiredIndicatorVisible(true);
        repeatPasswordField.setRequiredIndicatorVisible(true);

        Button registerButton = new Button("Registrarse");

        Span errorLabel = new Span();
        errorLabel.getStyle().set("color", "red");

        registerButton.addClickListener(e -> {
            String correo = emailField.getValue();
            String pass1 = passwordField.getValue();
            String pass2 = repeatPasswordField.getValue();

            // Validación de correo
            if (!correo.contains("@") || correo.isEmpty()) {
                errorLabel.setText("Introduce un correo válido.");
                return;
            }

            // Validación de contraseñas
            if (!pass1.equals(pass2) || pass1.isEmpty()) {
                errorLabel.setText("Las contraseñas no coinciden o están vacías.");
                return;
            }

            if (!esContrasenaSegura(pass1)) {
                errorLabel.setText("La contraseña debe tener mínimo 8 caracteres, incluir mayúsculas, minúsculas, números y símbolos.");
                return;
            }

            Usuario usuario = new Usuario(correo, pass1);

            try {
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.postForObject(
                        "http://localhost:8080/api/auth/start-register",
                        usuario,
                        String.class
                );

                SessionData.correoTemporal = correo;
                Notification.show(response);
                getUI().ifPresent(ui -> ui.navigate("confirmar")); // Redirige al formulario de código

            } catch (Exception ex) {
                errorLabel.setText("Error al registrar. ¿Quizás el correo ya existe?");
            }
        });

        add(
                title,
                emailField,
                passwordField,
                repeatPasswordField,
                registerButton,
                errorLabel,
                new RouterLink("¿Ya tienes cuenta? Iniciar sesión", LoginView.class)
        );
    }

    private boolean esContrasenaSegura(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^a-zA-Z0-9].*");
    }
}
