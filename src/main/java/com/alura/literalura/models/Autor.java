package com.alura.literalura.models;

public class Autor {

        String nombre;
        int anioNacimiento;
        int anioFallecimiento;

        public Autor(String nombre, int anioNacimiento, int anioFallecimiento) {
            this.nombre = nombre;
            this.anioNacimiento = anioNacimiento;
            this.anioFallecimiento = anioFallecimiento;
        }
        public Autor(){
        }
}
