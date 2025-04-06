package org.vaadin.example;

public class SessionData {
    public static String correoTemporal;

    private static String correo;
    private static boolean mfaCompletado = false;
    private static String rol;

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String nuevoCorreo) {
        correo = nuevoCorreo;
    }

    public static boolean isMfaCompletado() {
        return mfaCompletado;
    }

    public static void setMfaCompletado(boolean completado) {
        mfaCompletado = completado;
    }

    public static String getRol() {
        return rol;
    }

    public static void setRol(String nuevoRol) {
        rol = nuevoRol;
    }

    public static void limpiar() {
        correo = null;
        mfaCompletado = false;
        rol = null;
    }
}
