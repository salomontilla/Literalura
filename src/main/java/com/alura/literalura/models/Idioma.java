package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Idioma {
    ESPANOL("es","Español"),
    INGLES("en","Inglés"),
    FRANCES("fr","Francés"),
    PORTUGUES("pt","Portugués"),
    ITALIANO("it","Italiano");

    private String idiomaApi;
    private String idiomaEsp;

    Idioma (String idiomaApi, String idiomaEsp){
        this.idiomaApi = idiomaApi;
        this.idiomaEsp = idiomaEsp;
    }
    @JsonCreator
    public static Idioma obtenerIdioma(String idiomaApi) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaApi.equals(idiomaApi)) {
                return idioma;
            }
        }
        return null; // Devuelve null si no se encuentra el idioma
    }

    public static Idioma obtenerPorEspanol(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.idiomaEsp.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        return null;
    }
}
