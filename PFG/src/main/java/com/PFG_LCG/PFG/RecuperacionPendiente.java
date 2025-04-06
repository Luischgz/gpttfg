package com.PFG_LCG.PFG;

public class RecuperacionPendiente {
    private String correo;
    private String codigo;

    public RecuperacionPendiente(String correo, String codigo) {
        this.correo = correo;
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCodigo() {
        return codigo;
    }
}

