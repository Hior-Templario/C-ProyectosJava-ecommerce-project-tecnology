import { EstadoUsuario } from "./estadoUsuario";
import { Imagen } from "./imagen";
import { Persona } from "./persona";
import { Rol } from "./rol";

export interface Usuario{

idUsuario? : number;
nombreUsuario : string; 
correo : string; 
contrasenia : string; 
fechaRegistro : Date;

rol : Rol;
persona : Persona;
estadoUsuario : EstadoUsuario;
imagenUsuario: Imagen | null;

}