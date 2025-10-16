import { Injectable } from "@angular/core";

import { Adapter } from "./adapter";
import { Usuario } from "./usuario";

@Injectable({
    providedIn: 'root'
})

export class UsuarioPersonaAdapter implements Adapter<Usuario> {
    adapt(item: any): Usuario {
        return {
            idUsuario: item.idUsuario,
            nombreUsuario: item.nombreUsuario,
            correo: item.correo,
            contrasenia: item.contrasenia,
            fechaRegistro: item.fechaRegistro,
            imagenUsuario: item.imagenUsuario,
            persona: {
                idpersona: item.idpersona,
                nombresPersona: item.nombresPersona,
                apellidosPersona: item.apellidosPersona,
                tipoDocumento: item.tipoDocumento,
                numeroDocumento: item.numeroDocumento,
                fechaNacimiento: item.fechaNacimiento,
                sexo: item.sexo,
                telefono: item.telefono,
                correoSecundario: item.correoSecundario,
                direccion: item.direccion,
                ciudad: item.ciudad,
                pais: item.pais,
            },
                estadoUsuario: {
                    idEstado: item.idEstado,
                    nombreEstado: item.nombreEstado,
                    descripcion: item.descripcion
                },
            rol: {

                idRol : item.idRol,
                nombreRol : item.nombreRol,

            }
            
        };

    }
}