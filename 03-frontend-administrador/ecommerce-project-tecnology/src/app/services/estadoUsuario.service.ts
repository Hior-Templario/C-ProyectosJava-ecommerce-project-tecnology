import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EstadoUsuario } from "../models/estadoUsuario";

@Injectable({
    providedIn : 'root'
})

export class EstadoUsuarioService{

    private apiUrl = 'http://localhost:8080/api/estadosUsuario';

    constructor (private http: HttpClient){}

    obtenerEstadosUsuario(): Observable<EstadoUsuario[]> {
        return this.http.get<EstadoUsuario[]>(this.apiUrl);
    }

}