import { Imagen } from "./imagen";

export interface Categoria{

    idCategoria : number;

    nombreCategoria : string;
    
    descripcionCategoria :String;

    prefijoCategoria : string;

    imagenesCategoria? : Imagen[];



}

