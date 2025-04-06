package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("mfa")
public class ConfirmMFAView extends VerticalLayout {

    public ConfirmMFAView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        H2 title = new H2("Verificación de segundo factor (MFA)");

        TextField codigoField = new TextField("Código recibido por correo");
        codigoField.setRequired(true);

        Button confirmarBtn = new Button("Confirmar");

        confirmarBtn.addClickListener(e -> {
            String correo = SessionData.getCorreo();
            String codigo = codigoField.getValue().trim();

            if (correo == null || correo.isEmpty()) {
                Notification.show("No hay correo en sesión. Inicia sesión de nuevo.");
                getUI().ifPresent(ui -> ui.navigate(""));
                return;
            }

            if (codigo.isEmpty()) {
                Notification.show("Introduce el código recibido.");
                return;
            }

            try {
                RestTemplate restTemplate = new RestTemplate();
                String endpoint = String.format(
                        "http://localhost:8080/api/auth/confirm-mfa?correo=%s&codigo=%s",
                        correo, codigo
                );

                String respuesta = restTemplate.postForObject(endpoint, null, String.class);

                if (respuesta != null && respuesta.toLowerCase().contains("correcto")) {
                    SessionData.setMfaCompletado(true);
                    Notification.show("MFA confirmado. Bienvenido.");
                    getUI().ifPresent(ui -> ui.navigate("dashboard"));
                } else {
                    Notification.show(respuesta);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al confirmar el código MFA.");
            }
        });

        add(title, codigoField, confirmarBtn);
    }
}
