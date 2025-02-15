package com.example.ProgettoGestioneViaggiAziendali.Enum;

public enum StatoViaggio {
    IN_PROGRAMMA("In programma"),
    COMPLETATO("Completato");

    private final String descrizione;

    StatoViaggio(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}

