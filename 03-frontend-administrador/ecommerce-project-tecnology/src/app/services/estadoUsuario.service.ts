import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EstadoUsuario } from "../models/estadoUsuario";
import { environment } from "../../environments/environment";



@Injectable({
    providedIn : 'root'
})

export class EstadoUsuarioService{

    private apiUrl = (`${environment.backendUrl}/estadosUsuario`);
    

    constructor (private http: HttpClient){}

    obtenerEstadosUsuario(): Observable<EstadoUsuario[]> {
        return this.http.get<EstadoUsuario[]>(this.apiUrl);
    }

}