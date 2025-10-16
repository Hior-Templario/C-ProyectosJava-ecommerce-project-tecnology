// Importaciones básicas Angular
import { Component, Inject, OnInit, PLATFORM_ID } from "@angular/core";
import { CommonModule, isPlatformBrowser } from '@angular/common'; // Para directivas comunes como *ngForm
import { FormsModule } from '@angular/forms'; //  para soporte de formularios

// Importacion de modelos de datos
import { Producto } from "../../../models/producto";
import { Categoria } from "../../../models/categoria";
import { Marca } from "../../../models/marca";

// Importacion de servicios
import { CategoriaService } from "../../../services/categoria.service";
import { MarcaService } from "../../../services/marca.service";
import { ProductoService } from "../../../services/producto.service";
import { RouterModule } from "@angular/router";

// Importar

@Component({
    selector: 'app-listar-inventario', //Selector para usar en plantillas HTML
    standalone: true, // Componente autonomo (no necesita modulo padre)
    imports: [CommonModule, RouterModule, FormsModule], //Modulos requeridos
    templateUrl: './listar-inventario.component.html', //plantilla asociada
    styleUrls: ['./listar-inventario.component.css'], //Estilo CSS
    animations: []
})

export class ListarInventarioComponent implements OnInit {

    // Propiedades del component
    productos: Producto[] = []; // Lista de productos a mostrar
    categorias: Categoria[] = []; // Lista de categorias para filtros
    marcas: Marca[] = []; // Lista de marcas para filtros
    categoriaSeleccionada: number | null = null; // ID de categoria seleccionada
    marcaSeleccionada: number | null = null; // ID de marca seleccionada
    productoAEliminar: any = null; // Produxto seleccionado para eliminar
    mostrarModal: boolean = false;
    isBrowser: boolean;

    paginaActual = 0;
    tamanioPagina = 10;
    totalPaginas = 0;



    //Inyeccion de dependencias
    constructor(
        private productoService: ProductoService,
        @Inject(PLATFORM_ID) private platformId:Object
    ) { 
        // Detecta si estamos en navegador o SSR
        this.isBrowser = isPlatformBrowser(this.platformId)
    }

    // Metodo del ciclo de vida: se ejecuta al inicializar el componente
    ngOnInit(): void {
        this.cargarDatos(); // Carga inicial de datos
     
    }


    // Carga todos los datos necesarios
    cargarDatos(): void {
        //Carga productos
        this.productoService.obtenerProductosPaginados(this.paginaActual, this.tamanioPagina).subscribe({
            next: (response) => {
                console.log("Paginas recibidas", response)
                this.productos = response.content.map((item: any) => this.productoService.adapter.adapt(item));
                this.totalPaginas = response.totalPages;
            },
            error: (err) => console.error('error cargando productos:', err)
        })

    }

    // Confirmar eliminar

    confirmarEliminar(producto: any) {

        this.productoAEliminar = producto;
        this.mostrarModal = true;


    }
    // eliminar producto por id

    eliminarProducto() {
        if (this.productoAEliminar) {
            this.productoService.eliminarProducto(this.productoAEliminar.idProducto)
                .subscribe(() => {
                    this.productos = this.productos.filter(p => p.idProducto != this.productoAEliminar.idProducto)
                    this.mostrarModal = false;
                    this.productoAEliminar = null;
                });
        }
    }

    // Cancelar eliminar

    cancelarEliminar() {
        this.mostrarModal = false;
        this.productoAEliminar = null;
    }


    // Filtrar productos por categoria
    filtrarPorCategoria(): void {
        if (this.categoriaSeleccionada) {
            this.productoService.obtenerPorCategoria(this.categoriaSeleccionada).subscribe({
                next: (productos) => this.productos = productos,
                error: (err) => console.error('error fltrando productos:', err)
            });
        } else {
            // Si no hay categoria seleccionada, recarga todos los productos
            this.productoService.obtenerProductos().subscribe({
                next: (productos) => this.productos = productos
            });
        }
    }

    // Filtra productos por marca
    filtrarPorMarca(): void {
        if (this.marcaSeleccionada) {
            this.productoService.obtenerPorMarca(this.marcaSeleccionada).subscribe({
                next: (productos) => this.productos = productos,
                error: (err) => console.error('error fltrando Marca:', err)
            });
        } else {
            // Si no hay marca seleccionada, recarga todos los productos
            this.productoService.obtenerProductos().subscribe({
                next: (productos) => this.productos = productos
            });
        }
    }

    cambiarPagina(nuevaPagina: number) {
        if (nuevaPagina > 0 && nuevaPagina < this.totalPaginas) {
            this.paginaActual = nuevaPagina;
            this.cargarDatos()
        }
    }

}


