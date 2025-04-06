package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("cambiar")
public class ResetPasswordView extends VerticalLayout {

    public ResetPasswordView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        H2 title = new H2("Cambiar contraseña");

        TextField codigoField = new TextField("Código recibido");
        PasswordField nuevaPass = new PasswordField("Nueva contraseña");
        PasswordField repetirPass = new PasswordField("Repetir nueva contraseña");

        Button cambiarBtn = new Button("Cambiar contraseña");

        cambiarBtn.addClickListener(e -> {
            String correo = SessionData.correoTemporal;  // Usamos el correo guardado
            String codigo = codigoField.getValue();
            String nueva = nuevaPass.getValue();
            String repetir = repetirPass.getValue();

            if (!nueva.equals(repetir)) {
                Notification.show("Las contraseñas no coinciden.");
                return;
            }

            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = String.format(
                        "http://localhost:8080/api/auth/confirm-reset?correo=%s&codigo=%s&nuevaContrasena=%s",
                        correo, codigo, nueva
                );

                String respuesta = restTemplate.postForObject(url, null, String.class);
                Notification.show(respuesta);

                getUI().ifPresent(ui -> ui.navigate("")); // Vuelve al login

            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al cambiar la contraseña.");
            }
        });

        add(title, codigoField, nuevaPass, repetirPass, cambiarBtn);
    }
}
