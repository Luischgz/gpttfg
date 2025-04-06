package org.vaadin.example;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

public class VistaProtegida extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SessionData.isMfaCompletado() || SessionData.getCorreo() == null) {
            Notification.show("No tienes acceso. Inicia sesión y completa el MFA.");
            event.forwardTo("");  // Redirige al login
        }
    }
}
