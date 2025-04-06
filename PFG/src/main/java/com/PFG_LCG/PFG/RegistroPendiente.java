package com.PFG_LCG.PFG;

public class RegistroPendiente {
    private String correo;
    private String contrasenaHash;
    private String codigo;

    public RegistroPendiente(String correo, String contrasenaHash, String codigo) {
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public String getCodigo() {
        return codigo;
    }

    public String toJson() {
        if (correo == null || contrasenaHash == null || codigo == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }
        return String.format("{\"correo\":\"%s\", \"contrasenaHash\":\"%s\", \"codigo\":\"%s\"}", correo, contrasenaHash, codigo);
    }
}