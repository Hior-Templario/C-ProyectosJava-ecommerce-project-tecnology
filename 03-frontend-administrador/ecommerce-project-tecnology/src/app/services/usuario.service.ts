// Importa el decorador injectable para declarar servicios que se puedan inyectar en otros componentes o servicios 
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Usuario } from "../models/usuario";
import { UsuarioPersonaAdapter } from "../models/usuario-persona-adapter";

import { UsuarioActualizacion } from "../models/usuarioActualizacion";
import { environment } from "../../environments/environment";





@Injectable({
    providedIn: 'root' // Angular crearà una unica instancia disponible en todas la aplicaciòn
})

export class UsuarioService{

    // Url base del backend para acceder a los endpoints relacionados con el producto
    private apiUrl = (`${environment.backendUrl}/usuarios`);
    

    // inyeccion del servicio HttpClient para poder realizar peticiones HTTP
    constructor ( private http:HttpClient,
                  public adapter: UsuarioPersonaAdapter
    ){}

    
    // Mètodo para obtener todos los usuarios del backend
    obtenerUsuarios(): Observable<Usuario[]>{
       return this.http.get<any[]>(this.apiUrl).pipe(
            map(data => data.map(item => this.adapter.adapt(item)))
        )
    }

    // Metodo para obtener todos los usuarios paginados del backend
    obtenerUsuariosPersonasPaginados(page: number = 0, size: number = 20): Observable<any> {
    return this.http.get(`${this.apiUrl}/usuariosPersonasPaginados?page=${page}&size=${size}`)
    }
       
    // Metodo para obtener usuario
    obtenerUsuario(idUsuario: number): Observable<Usuario>{
    
      const url = `${this.apiUrl}/perfilUsuario/${idUsuario}`;
      console.log("id Usuario: " + idUsuario)
      return this.http.get<Usuario>(url)
    }

    // Metodo para obtener usuario y datos persona
    obtenerUsuarioPersona(idUsuario: number): Observable<Usuario>{
    
      const url = `${this.apiUrl}/perfilUsuarioPersona/${idUsuario}`;
      console.log("id Usuario persona: " + idUsuario)
      return this.http.get<any>(url).pipe(
        map(item => this.adapter.adapt(item))
      )
    }

    // Metodo para crear un nuevo Usuario Persona en el backend
    crearUsuario(usuario: any): Observable<any>{
      return this.http.post<Usuario>(`${this.apiUrl}/crearUsuarioPersona`,usuario);
    }

    // Metodo para actualizar producto
    actualizarUsuario(idUsuario: number, usuarioActualizacion: UsuarioActualizacion): Observable<Usuario>{
      return this.http.put<Usuario>(`${this.apiUrl}/modificarUsuario/${idUsuario}`, usuarioActualizacion);

    } 




}