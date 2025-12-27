import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Rol } from "../models/rol";
import { environment } from "../../environments/environment";



@Injectable ({
    providedIn : 'root'
})

export class RolService{

    private apiUrl = (`${environment.backendUrl}/roles`);
    
    constructor (private http: HttpClient){}

    obtenerRoles(): Observable<Rol[]>{
        return this.http.get<Rol[]>(this.apiUrl);
    }
}