import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/enviroment';

@Injectable({
  providedIn: 'root'
})
export class ImagenService {

  private apiUrl = (`${environment.backendUrl}/imagenes`);
  

  constructor(private http: HttpClient){}


  subirImagen(archivo: File, tipo: string, idEntidad: number) {
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('tipo', tipo);
    formData.append('idEntidad', idEntidad.toString());

    return this.http.post(`${this.apiUrl}/subir`, formData);
  }

  eliminarPorEntidad(tipoEntidad: string, idEntidad: number) {
    return this.http.delete(
      `${this.apiUrl}/eliminar-por-entidad?tipo=${tipoEntidad}&idEntidad=${idEntidad}`
    )
  }


}
