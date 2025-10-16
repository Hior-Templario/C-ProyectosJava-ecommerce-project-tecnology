import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Categoria } from "../models/categoria";

@Injectable({
    providedIn : 'root'
})

export class CategoriaService{

   private apiUrl = 'http://localhost:8080/api/categorias';
   

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

