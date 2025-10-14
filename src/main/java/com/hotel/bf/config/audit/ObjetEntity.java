package com.hotel.bf.config.audit;
/**
 * Enum for the different audit actions
 */
public enum ObjetEntity {
    AGENCE("Agence"),
    FILIALE("Filiale"),
    USER("Utilisateur"),
    PRODUIT("Produit"),
    Mailling("Envoie mail"),
    CLIENT("Client"),
    MOTIF("Motif"),
    MATERIEL("Materiel"),
    TYPECLIENT("TypeClient"),
    TYPEMATERIEL("TypeMateriel"),
    SOUSCRIPTION("Souscription"),
    FOURNISSEUR("Fournisseur"),
    PROFIL("Profil"),
    PROFORMA("province"),
    REGION("region"),
    PROVINCE("province"),
    COMMUNE("commune"),
    ENTETE("Entete");


    private String value;

    ObjetEntity(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }
}
