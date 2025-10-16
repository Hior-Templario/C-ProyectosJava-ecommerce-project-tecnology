import { Injectable } from "@angular/core";
import { Producto } from "./producto";
import { Adapter } from "./adapter";

@Injectable({
    providedIn: 'root'
})

export class ProductoAdapter implements Adapter<Producto> {
    adapt(item: any): Producto {
        return {
            idProducto: item.idProducto,
            codigoProducto: item.codigoProducto,
            nombreProducto: item.nombreProducto,
            descripcion: item.descripcion,
            precio: item.precio,
            fechaRegistro: item.fechaRegistro,
            fechaActualizacion: item.fechaActualizacion,
            stock: item.stock,
            imagenesProducto: item.imagenesProducto,
            categoria: {
                idCategoria: item.idCategoria,
                nombreCategoria: item.nombreCategoria,
                descripcionCategoria: item.descripcionCategoria,
                prefijoCategoria: item.prefijoCategoria,
                imagenesCategoria: item.imagenesCategoria
            },
            marca: {
                idMarca: item.idMarca,
                nombreMarca: item.nombreMarca,
                imagenesMarca: item.imagenesMarca

            }
        };

    }
}


