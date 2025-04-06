package com.PFG_LCG.PFG;

public class MFAPendiente {

    private String correo;
    private String codigo;

    public MFAPendiente(String correo, String codigo) {
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
