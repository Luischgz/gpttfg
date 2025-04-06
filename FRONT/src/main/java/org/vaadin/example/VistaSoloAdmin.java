package org.vaadin.example;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;

public class VistaSoloAdmin extends VistaProtegida {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        super.beforeEnter(event); // Primero verifica si la sesión está iniciada y el MFA pasado

        // Luego validamos que sea admin
        if (!"admin".equalsIgnoreCase(SessionData.getRol())) {
            Notification.show("Acceso restringido a administradores.");
            event.forwardTo("dashboard");  // Redirige a vista de usuario
        }
    }
}
