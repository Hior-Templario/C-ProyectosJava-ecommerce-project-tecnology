import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Rol } from "../models/rol";

@Injectable ({
    providedIn : 'root'
})

export class RolService{

    private apiUrl ='http://localhost:8080/api/roles';

    constructor (private http: HttpClient){}

    obtenerRoles(): Observable<Rol[]>{
        return this.http.get<Rol[]>(this.apiUrl);
    }
}