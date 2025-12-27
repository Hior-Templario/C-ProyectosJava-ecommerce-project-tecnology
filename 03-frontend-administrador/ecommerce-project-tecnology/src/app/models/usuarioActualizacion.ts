import { PersonaActualizacion } from "./personaActualizacion";

export interface UsuarioActualizacion{
    nombreUsuario : string; 
    correo : string; 

    idRol : number;
    idEstado : number;
    
    Persona : PersonaActualizacion;

}