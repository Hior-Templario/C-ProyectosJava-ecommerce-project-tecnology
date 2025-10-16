package com.development.ecommerce_tecnology.dto;


import java.time.LocalDateTime;

public class MovimientoInventarioDto {


    private Long idMovimiento;
    private String tipoMovimiento;
    private Integer cantidad;
    private String observaciones;
    private LocalDateTime fechaMovimiento;


    private Long idProducto;
    private String nombreProducto;

    public MovimientoInventarioDto() {
    }

    public MovimientoInventarioDto(Long idMovimiento, String tipoMovimiento, Integer cantidad, String observaciones, LocalDateTime fechaMovimiento, Long idProducto, String nombreProducto) {
        this.idMovimiento = idMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.fechaMovimiento = fechaMovimiento;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
