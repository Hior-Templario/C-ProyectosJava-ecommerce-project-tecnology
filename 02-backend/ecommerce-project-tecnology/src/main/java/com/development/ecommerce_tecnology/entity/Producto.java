package com.development.ecommerce_tecnology.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 5000)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "stock", nullable = false, length = 100)
    private int stock;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @ManyToOne
    @JoinColumn(name ="id_categoria", nullable = false)
    private Categoria categoria;

   // @NotNull(message = " La maca es oligatoria")
    @ManyToOne()
    @JoinColumn(name ="id_marca", nullable = false)
    private Marca marca;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
