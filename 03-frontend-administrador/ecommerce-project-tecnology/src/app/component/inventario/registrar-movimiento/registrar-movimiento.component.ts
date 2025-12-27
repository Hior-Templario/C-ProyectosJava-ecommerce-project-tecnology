import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../../services/producto.service';
import { MovimientoInventarioService } from '../../../services/movimientoInventario.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { debounceTime, distinctUntilChanged, of, switchMap } from 'rxjs';
import { Producto } from '../../../models/producto';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-registrar-movimiento',
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './registrar-movimiento.component.html',
  styleUrls:[ './registrar-movimiento.component.css']
})
export class RegistrarMovimientoComponent implements OnInit{

  formMovimientoInventario!: FormGroup;
  productosFiltrados: Producto [] = [];
  buscadorControl= new FormControl('');

  constructor(
    private formBuilder: FormBuilder,// inyeccion de dependencia para construir formularios reactivos.
    private movimientoInventarioService: MovimientoInventarioService,
    private productoService : ProductoService //  // Buscar productp
  
  ){}



  ngOnInit(): void {
   this.inicializarForm();
   this.configurarAutocompletado();
  }


  
  private inicializarForm(): void{

    console.log("Pagina abierta");

   this.formMovimientoInventario = this.formBuilder.group({
    
          idProducto: [null, Validators.required],  
          
          tipoMovimiento: ["", [
              Validators.required,
              Validators.minLength(2),
            ]],
          cantidad:  [0, [
              Validators.required,
            ]],
          observaciones:  ["", [
              Validators.required,
              EcommerceValidador.noVacioConEspacios
            ]],
          fechaMovimiento:  ["", []],

    
  })
  } 

  // Configurar el autocompletado del input de busqueda

  private configurarAutocompletado(): void {
    this.buscadorControl.valueChanges
    .pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap( valor =>
        valor && valor.trim() !== ''
        ? this.productoService.buscarProducto(valor)
        : of([])
      )
    )
    .subscribe(productos => {
      this.productosFiltrados = productos;
    });
  }


  // Selecciona un producto de la lista filtrada
  seleccionarProducto (producto: Producto): void{
    this.formMovimientoInventario.patchValue({idProducto: producto})
    this.productosFiltrados = []
    this.buscadorControl.setValue(`${producto.codigoProducto} - ${producto.nombreProducto}`, { emitEvent: false });

  }

  // Envia el formulario al bakend

  onSubmit(): void {
    if(this.formMovimientoInventario.invalid){
      this.formMovimientoInventario.markAllAsTouched();
      console.log('Formulario inválido');
      return;
    }

    const movimiento = this.formMovimientoInventario.value;


    if (typeof movimiento.idProducto === 'object' && movimiento.idProducto.idProducto){
      movimiento.idProducto = movimiento.idProducto.idProducto;
    }


    console.log('Enviando movimiento:', movimiento);

    this.movimientoInventarioService.RegistrarMovimientoInventario(movimiento)
    .subscribe({
      next: res => {
        console.log('Movimiento registrado', res);
        this.formMovimientoInventario.reset();
        this.buscadorControl.reset();

      },
      error: err => {
        console.error('Error al registrar movmiento', err);
      }
    });
  }

  get f():{
    idProducto: AbstractControl;
    tipoMovimiento: AbstractControl;
    cantidad : AbstractControl;
    observaciones : AbstractControl;
    fechaMovimiento: AbstractControl;
  }
{
    return this.formMovimientoInventario.controls as any;
  }

}
