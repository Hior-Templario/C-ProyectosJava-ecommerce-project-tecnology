// Importa el decorador injectable para declarar servicios que se puedan inyectar en otros componentes o servicios 
import { Injectable } from "@angular/core";
// Importa el modelo Producto, que representa la estructura de los datos del producto
import { Producto } from "../models/producto";
// Importa la clase observable de rxjs para manejar operaciones asincronas 
import { map, Observable } from "rxjs";
// Importa el cliente HTTP de Angular para hacer peticiones al servidor de backend
import { HttpClient } from "@angular/common/http";
import { ProductoAdapter } from "../models/producto-adapter";
import { environment } from "../../environments/enviroment";



// Marca esta clase como inyectable y diponible globalmente (Singleton)
@Injectable({
    providedIn: 'root' // Angular crearà una unica instancia disponible en todas la aplicaciòn
})

export class ProductoService{

    // Url base del backend para acceder a los endpoints relacionados con el producto

    private apiUrl = (`${environment.backendUrl}/productos`);
    

    
    // inyeccion del servicio HttpClient para poder realizar peticiones HTTP
    constructor ( private http: HttpClient,
                  public adapter: ProductoAdapter
                  
    ){}


    // Mètodo para obtener todos los productos paginados del backend
    obtenerProductosPaginados(page: number = 0, size: number = 20): Observable<any> {
   return this.http.get(`${this.apiUrl}/paginados?page=${page}&size=${size}`)
    }


    obtenerProductos(): Observable<Producto[]>{
        return this.http.get<any[]>(this.apiUrl).pipe(
            map(data => data.map(item => this.adapter.adapt(item)))
        )
    }

    // Mètodo para obtener por nombreProducto o por codigoProducto del backend
    buscarProducto(query:string): Observable<Producto[]>{
        return this.http.get<Producto[]>(`${this.apiUrl}/buscar?q=${query}`)

    }

    // Mètodo para crear un nuevo producto en el backend
    crearProducto(producto: any): Observable<any>{
        return this.http.post<Producto>(`${this.apiUrl}/crearProducto`,producto);
    }

    // Metodo para obtener producto
    obtenerProducto(idProducto: number): Observable<Producto>{

        const url = `${this.apiUrl}/detallesProducto/${idProducto}`;
        console.log("id producto", idProducto)
        return this.http.get<any>(url).pipe(
            map(item => this.adapter.adapt(item))
        )
    }

    // Metodo para eliminar producto
    eliminarProducto(idProducto: number): Observable<void>{
            return this.http.delete<void>(`${this.apiUrl}/eliminarProducto/${idProducto}`);

    }

    // Mètodo para obtener productos filtrados por categoria
    obtenerPorCategoria(idCategoria : number ):Observable<Producto[]>{
        return this.http.get<Producto[]>(`${this.apiUrl}/categoria/${idCategoria}`)
    }

    // Mètodo para obtener productos filtrados por categoria
    obtenerPorMarca(idMarca : number){
        return this.http.get<Producto[]>(`${this.apiUrl}/marca/${idMarca}`)
    }

    
}