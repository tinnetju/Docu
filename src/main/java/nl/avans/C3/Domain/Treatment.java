/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.Domain;

/**
 *
 * @author Thom
 */
public class Treatment {
    private int behandelCode;
    private String behandelingNaam;
    private String aantalSessies;
    private String sessieDuur;
    private double tariefBehandeling;
    
    public Treatment(int behandelCode, String behandelingNaam, String aantalSessies, String sessieDuur, double tariefBehandeling){
        this.behandelCode = behandelCode;
        this.behandelingNaam = behandelingNaam;
        this.aantalSessies = aantalSessies;
        this.sessieDuur = sessieDuur;
        this.tariefBehandeling = tariefBehandeling;
    }

    public int getBehandelCode() {
        return behandelCode;
    }

    public String getBehandelingNaam() {
        return behandelingNaam;
    }

    public String getAantalSessies() {
        return aantalSessies;
    }

    public String getSessieDuur() {
        return sessieDuur;
    }

    public double getTariefBehandeling() {
        return tariefBehandeling;
    }

    public void setBehandelCode(int behandelCode) {
        this.behandelCode = behandelCode;
    }

    public void setBehandelingNaam(String behandelingNaam) {
        this.behandelingNaam = behandelingNaam;
    }

    public void setAantalSessies(String aantalSessies) {
        this.aantalSessies = aantalSessies;
    }

    public void setSessieDuur(String sessieDuur) {
        this.sessieDuur = sessieDuur;
    }

    public void setTariefBehandeling(double tariefBehandeling) {
        this.tariefBehandeling = tariefBehandeling;
    }
}
