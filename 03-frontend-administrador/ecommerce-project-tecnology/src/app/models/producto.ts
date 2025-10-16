import { Data } from "@angular/router";
import { Categoria } from "./categoria";
import { Marca } from "./marca";
import { Imagen } from "./imagen";

export interface Producto{
  
idProducto? : number;
codigoProducto:string;
nombreProducto : string;
descripcion: string;
precio : number;
fechaRegistro : Date;
fechaActualizacion  : Date;
stock : number;
imagenesProducto? : Imagen[];

categoria: Categoria;
marca: Marca;

}