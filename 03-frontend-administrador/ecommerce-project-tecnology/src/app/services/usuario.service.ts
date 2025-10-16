// Importa el decorador injectable para declarar servicios que se puedan inyectar en otros componentes o servicios 
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Usuario } from "../models/usuario";
import { UsuarioPersonaAdapter } from "../models/usuario-persona-adapter";



@Injectable({
    providedIn: 'root' // Angular crearà una unica instancia disponible en todas la aplicaciòn
})

export class UsuarioService{

    // Url base del backend para acceder a los endpoints relacionados con el producto
    private apiUrl = 'http://localhost:8080/api/usuarios';

    // inyeccion del servicio HttpClient para poder realizar peticiones HTTP
    constructor ( private http:HttpClient,
        private adapter: UsuarioPersonaAdapter
    ){}

    // Mètodo para obtener todos los usuarios del backend
    obtenerUsuarios(): Observable<Usuario[]>{
       return this.http.get<any[]>(this.apiUrl).pipe(
            map(data => data.map(item => this.adapter.adapt(item)))
        )
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



}