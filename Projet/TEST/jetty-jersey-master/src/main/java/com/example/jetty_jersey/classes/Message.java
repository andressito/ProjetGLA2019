package com.example.jetty_jersey.classes;

import java.util.Date;

public class Message {
    public String idMessage;
    public String contenu;
    public String idEmetteur;
    public String idDestinataire;
    public Date dateEnvoi;

    public Message(String idMessage,String contenu, String idEmetteur, String idDestinataire, Date date){
        this.idMessage= idMessage;
        this.contenu=contenu;
        this.idEmetteur=idEmetteur;
        this.idDestinataire=idDestinataire;
        this.dateEnvoi=date;
    }
    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setIdEmetteur(String idEmetteur) {
        this.idEmetteur = idEmetteur;
    }

    public void setIdDestinataire(String idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getIdMessage() {
        return idMessage;
    }

    public String getContenu() {
        return contenu;
    }

    public String getIdEmetteur() {
        return idEmetteur;
    }

    public String getIdDestinataire() {
        return idDestinataire;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }
}
