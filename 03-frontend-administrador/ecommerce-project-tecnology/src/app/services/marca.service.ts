import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Marca } from "../models/marca";
import { environment } from "../../environments/environment";



@Injectable ({
    providedIn : 'root'
})

export class MarcaService{

    private apiUrl = (`${environment.backendUrl}/marcas`);

    constructor (private http: HttpClient){}

    // Metodo para obtener todas las cmarcas del backend
    obtenerMarcas(): Observable<Marca[]>{
        return this.http.get<Marca[]>(this.apiUrl);
    }

    // Metodo parta crear una nueva marca en el backend
    crearMarca(marca:any): Observable <any> {
        return this.http.post<Marca>(`${this.apiUrl}/crearMarca`,marca);

    }
}