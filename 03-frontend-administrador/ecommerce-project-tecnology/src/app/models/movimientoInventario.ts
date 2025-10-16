import { Producto } from "./producto";

export interface MovimientoInventario{

    idMovimiento : number;
    tipoMovimiento : string;
    cantidad : number;
    observaciones : string;
    fechaMovimiento : Date;
    producto : number | null;


}