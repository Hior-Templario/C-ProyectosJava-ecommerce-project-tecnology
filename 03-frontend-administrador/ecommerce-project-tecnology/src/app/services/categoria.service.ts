import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Categoria } from "../models/categoria";
import { environment } from "../../environments/enviroment";

@Injectable({
    providedIn : 'root'
})

export class CategoriaService{

   private apiUrl = (`${environment.backendUrl}/categorias`);
   

   constructor(private http: HttpClient){}

   // Mètodo para obtener todas las categorias del backend
   obtenerCategorias(): Observable<Categoria[]>{
      return this.http.get<Categoria[]>(this.apiUrl);
 }

 // Metodo para crear una nueva categoria en el backend
 crearCategoria(categoria: any): Observable <any>{
    return this.http.post<Categoria>(`${this.apiUrl}/crearCategoria`,categoria);
 }
}

