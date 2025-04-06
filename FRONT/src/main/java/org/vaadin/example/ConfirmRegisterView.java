package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("confirmar")
public class ConfirmRegisterView extends VerticalLayout {

    public ConfirmRegisterView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        H2 title = new H2("Confirmar Registro");

        TextField codigoField = new TextField("Código de verificación");
        codigoField.setRequired(true);

        Button confirmarBtn = new Button("Confirmar");

        confirmarBtn.addClickListener(e -> {
            String codigo = codigoField.getValue();
            String correo = SessionData.correoTemporal;

            if (correo == null || correo.isEmpty()) {
                Notification.show("No se encontró el correo de registro. Por favor, vuelve a registrarte.");
                getUI().ifPresent(ui -> ui.navigate("register"));
                return;
            }

            try {
                RestTemplate restTemplate = new RestTemplate();
                String endpoint = String.format(
                        "http://localhost:8080/api/auth/confirm-register?correo=%s&codigo=%s",
                        correo, codigo
                );

                String respuesta = restTemplate.postForObject(endpoint, null, String.class);

                Notification.show(respuesta);
                getUI().ifPresent(ui -> ui.navigate("")); // Vuelve al login

            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al confirmar el registro");
            }
        });

        add(title, codigoField, confirmarBtn);
    }
}
