// Importa HttpClient para que el servicio pueda comunicarse al backend
import { HttpClient } from '@angular/common/http';
// Importa decoradores y funciones necesarias para la inyeccion de dependencias y la detección de plataforma
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
// Importa observable y tap de RxJs para manejar respuestas y ejecutar efectos secundarios
import { Observable, tap } from 'rxjs';
// Importa isPlatformBrowser para determinar si la aplicación se ejecuta en un navegador
import { isPlatformBrowser } from '@angular/common';
import { environment } from '../../environments/enviroment';

// Define la interfaz LoginResponse que presenta la estructura de los datos que devuelve el backend al iniciar sesión
interface LoginResponse{
  
  idUsuario: number;     // Representa el ID del usuario que ha iniciado sesión 
  nombreUsuario : string // Contiene el nombre del usuario
  roles: string[]        // Lista de roles que tiene el usuario (por ejemplo, ADMIN, USER)
  token: string;         //  Token JWT que se usará para autenticación

}

// Declara el servicio como inyectable en toda la aplicación
@Injectable({
  providedIn: 'root'
})
export class AutenticacionService {

  // Define la URL del endpoint de login en el backend
   private apiUrl = (`${environment.backendUrl}/login`);
   // Define una variable que indica si la aplicación se ejecuta en un navegador
   private isBrowser: boolean;
   
   // Constructor del servicio
  constructor(

    private http: HttpClient,     // Inyecta HttpClient para poder hacer peticiones
    @Inject(PLATFORM_ID)private platformId: Object // Inyecta PLATFORM_ID para detectar la plataforma en la que se ejecuta la app
  ) {
    // Determina si la aplicación se ejecuta en un navegador y asigna el resultado a la variable isBrowser
    this.isBrowser = isPlatformBrowser(this.platformId)
  }

  // Método que realiza el login de usuario
  login(username: string, password: string): Observable<LoginResponse>{
    // Envía una petición POST al backend con el nombre de usuario y la contraseña
    return this.http.post<LoginResponse>(this.apiUrl,{
      nombreUsuario: username, 
      contrasenia: password
    }).pipe(
      // Ejecuta efectos secundarios al recibir respuesta
      tap(res => {
        if(this.isBrowser){
          // Almacena el token y el ID del usuario en localStorage si la aplicación se ejecuta en el navegador
          localStorage.setItem('token', res.token);
          localStorage.setItem('idUsuario', res.idUsuario.toString())
        }
      })
    )

  }

    // Método que cierra la sesión del usuario
    logout(){
      if(this.isBrowser){
        // Elimina el token y el ID del usuario del localStorage
      localStorage.removeItem('token');
      localStorage.removeItem('idUsuario');
      }
    }

    // Método que obtiene el token del usuario logueado
    getToken(): string | null{
      if(this.isBrowser){
        // Retorna el token si existe en el localStorage 
      return localStorage.getItem('token');
      }
      // Retorna null si no hay token o si no está en un navegador
      return null;
    }

    // Método que verifica si el usuario esta logueado
    isLoggedIn():boolean{
      // Retorna true si existe un token, false en caso contrario
      return !!this.getToken();
    }

    // Método que obtiene el ID del usuario logueado
    getUsuarioId(): number | null {
      if(this.isBrowser){
        const id = localStorage.getItem('idUsuario');
        // convierte el ID de string a número si existe, o retorna null
        return id ? +id : null; 
      }
      // Retorna null si no hay ID o si no está en un navegador
      return null;
    }
  
}
