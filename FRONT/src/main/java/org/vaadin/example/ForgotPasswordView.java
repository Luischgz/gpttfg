package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.web.client.RestTemplate;

@Route("recuperar")
public class ForgotPasswordView extends VerticalLayout {

    public ForgotPasswordView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        H2 title = new H2("Recuperar contraseña");

        EmailField correoField = new EmailField("Correo electrónico");
        correoField.setRequired(true);

        Button enviarBtn = new Button("Enviar código");

        enviarBtn.addClickListener(e -> {
            String correo = correoField.getValue();

            // Guardamos el correo para reutilizarlo en la siguiente vista
            SessionData.correoTemporal = correo;

            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8080/api/auth/start-reset?correo=" + correo;
                String respuesta = restTemplate.postForObject(url, null, String.class);
                Notification.show(respuesta);
                getUI().ifPresent(ui -> ui.navigate("cambiar")); // Va a la siguiente vista
            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al enviar el código");
            }
        });

        add(title, correoField, enviarBtn, new RouterLink("Volver al login", LoginView.class));
    }
}
