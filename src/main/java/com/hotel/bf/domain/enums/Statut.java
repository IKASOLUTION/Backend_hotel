package com.hotel.bf.domain.enums;

import lombok.Getter;

@Getter
public enum Statut {

    ACTIF("Actif"),
    INACTIF("Inactif"),
    SUPPRIME("Supprimer"),
    SUSPENDUE("Suspendue"),
    APPREHENDEE("Apprehendée");

    private final String label;

    Statut(String label) {
        this.label = label;
    }

}
