package com.PFG_LCG.PFG;

public class CampaignStatsDTO {

    private String nombreCampana;
    private int total;
    private int clics;

    public CampaignStatsDTO(String nombreCampana, int total, int clics) {
        this.nombreCampana = nombreCampana;
        this.total = total;
        this.clics = clics;
    }

    public String getNombreCampana() {
        return nombreCampana;
    }

    public void setNombreCampana(String nombreCampana) {
        this.nombreCampana = nombreCampana;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getClics() {
        return clics;
    }

    public void setClics(int clics) {
        this.clics = clics;
    }
}
