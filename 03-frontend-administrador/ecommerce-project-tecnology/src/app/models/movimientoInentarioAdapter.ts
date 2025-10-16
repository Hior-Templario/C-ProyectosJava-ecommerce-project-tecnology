import { Injectable } from "@angular/core";
import { Adapter } from "./adapter";
import { MovimientoInventario } from "./movimientoInventario";

@Injectable({
    providedIn: 'root'
})

export class MovimientoInventarioAdapter implements Adapter<MovimientoInventario> {

    // convierte respuesta del backend -> modelo del frontend
    adapt(item: any):MovimientoInventario {
        return{
            idMovimiento : item.idMovimiento ,
            tipoMovimiento : item.tipoMovimiento,
            cantidad : item.cantidad,
            observaciones : item.observaciones,
            fechaMovimiento : item.fechaMovimiento,
            producto: item.producto ? item.producto.idProducto:null
  
        };
    }
    
}