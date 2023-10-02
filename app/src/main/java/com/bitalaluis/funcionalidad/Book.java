package com.bitalaluis.funcionalidad;

public class Book {
    private String titulo;
    private String autor;
    private String subtitulo;
    private String isbn;
    private String anio;
    private Float precio;

    public Book(String titulo, String autor, String subtitulo, String isbn, String anio, Float precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.subtitulo = subtitulo;
        this.isbn = isbn;
        this.anio = anio;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    // Agrega getters y setters para otros campos si es necesario
}
