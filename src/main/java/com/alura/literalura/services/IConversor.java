package com.alura.literalura.services;

public interface IConversor {
    <T> T convertirDatos(String json, Class<T> clase);
}
