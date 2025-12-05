import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/enviroment";


@Injectable({
    providedIn: 'root' // Angular crearà una unica instancia disponible en todas la aplicaciòn
})

export class MovimientoInventarioService{

    
  // Url base del backend para acceder a los endpoints relacionados con el producto

  private apiUrl = (`${environment.backendUrl}/movimientoInventario`);

    constructor ( private http:HttpClient,
    ){}


    // Metodo para crear un nuevo movimientoInventario en el backend
    RegistrarMovimientoInventario(movimientoInventario: any): Observable<any>{
        return this.http.post<any>(`${this.apiUrl}/registrarMovimientoInventario`, movimientoInventario)
    }
}