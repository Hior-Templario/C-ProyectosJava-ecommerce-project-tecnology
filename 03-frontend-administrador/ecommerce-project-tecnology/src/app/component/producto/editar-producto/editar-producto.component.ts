import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { Producto } from '../../../models/producto';

import { error } from 'console';
import { ProductoActualizacion } from '../../../models/productoActualiacion';
import { CargarImagenComponent } from "../../imagenes/cargar-imagen/cargar-imagen.component";

@Component({
  selector: 'app-editar-producto',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './editar-producto.component.html',
  styleUrls: ['./editar-producto.component.css'] //Estilo CSS asociados
})
export class EditarProductoComponent {
  
  formProducto!: FormGroup; //Grupo de formularios reactivos.
  idProducto!: number; // Almacena el ID del producto a editar PROVENIENTE DE LA RUTA

  constructor(
     private formBuilder: FormBuilder, // inyeccion de FormBuilder para construir formularios reactivos.
     private productoService: ProductoService, // Servicio para obtener productos
     private router: Router, // Router de Angular para navegacoón programática
     private route: ActivatedRoute, // Inyeccion de ActivatedRoute para  obtener parametros de la ruta
  ) { }



  ngOnInit():void {
    // Obiene id de Url
    this.idProducto = Number(this.route.snapshot.paramMap.get('idProducto'));
     // Valida que el ID sea vàlido antes de continuar
    if(!this.idProducto || this.idProducto <=0){
      console.error('ID de producto inválido');
      this.router.navigate(['/listaProductos'])
      return;
    }
    // Inicializa el formulario con grupos y campos aninados
    this.inicializarForm();
     // llama al servicio para obtener producto
    this.cargarProducto();
  }


    inicializarForm(): void{

      // Define la estructura del formulario reactico y sus validaciones
      this.formProducto = this.formBuilder.group({
      nombreProducto: ["", [
        Validators.required,
        Validators.minLength(2),
      ]],
      descripcion: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],
      precio: [null, [
        Validators.required,
        Validators.min(0.01)
      ]],

      idCategoria: [null, Validators.required], // Categoria asociada al producto

      idMarca: [null, Validators.required], // Marca asociada al producto

    }); 
    
  }

  cargarProducto(): void {
    // Consume el servicio para obtener el producto por su ID
    this.productoService.obtenerProducto(this.idProducto).subscribe({
      next: (producto: Producto) => {
      // Carga los valores del producto en el formulario
      this.formProducto.patchValue({
        nombreProducto: producto.nombreProducto,
        descripcion: producto.descripcion,
        precio: producto.precio,
        idCategoria: producto.categoria.idCategoria,
        idMarca: producto.marca.idMarca
    });
  },
  error: (err) => {
      // Manejo de error si el producto no existe o falla la peticiòn
      console.error('Error cargando producto', err);
  alert('Producto no encontrado');
     this.router.navigate(['/listaProductos']);
    }
  });

  }

  actualizarProducto(): void {
    // Construye el objeto de actualizacion a partir del formulario
    const productoActualizacion: ProductoActualizacion = this.formProducto.value;

    // Envia la solicitud de actualizaciòn al backend
    this.productoService.actualizarProducto(this.idProducto, productoActualizacion).subscribe({
      next: (res) => {
        console.log('Producto actualizado', res);
        // Re dirige al detalle del producto actualizado
        this.router.navigate(['/detallesProducto', this.idProducto]); 
      },
      error: (err) => console.error('Error actualizado', err)
    });
    
  }

}
