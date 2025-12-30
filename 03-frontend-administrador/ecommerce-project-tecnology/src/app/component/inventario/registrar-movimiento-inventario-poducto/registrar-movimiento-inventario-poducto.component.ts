import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../../services/producto.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { MovimientoInventarioService } from '../../../services/movimientoInventario.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { error } from 'console';
import { CommonModule } from '@angular/common';
import { Producto } from '../../../models/producto';

@Component({
  selector: 'app-registrar-movimiento-inventario-poducto',
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './registrar-movimiento-inventario-poducto.component.html',
  styleUrls:['./registrar-movimiento-inventario-poducto.component.css']
})
export class RegistrarMovimientoInventarioPoductoComponent implements OnInit {

  formMovimientoInventario!: FormGroup;
  idProducto! : number;

  constructor(
    private formBuilder: FormBuilder,// inyeccion de dependencia para construir formularios reactivos.
    private route: ActivatedRoute,// Inyeccion de ActivateRoute para obtener parametros de la ruta
    private movimientoInventarioService: MovimientoInventarioService,
    private router : Router, // Router de Angular para navegación prográmatica
    private productoService :ProductoService,

){}

ngOnInit(): void {
  this.idProducto = Number(this.route.snapshot.paramMap.get('idProducto'))

  // Incializa el formulario con grupos y campos anidados
   this.inicializarForm();
   this.cargaProducto();

}

  private inicializarForm(): void{


   this.formMovimientoInventario = this.formBuilder.group({
    
          idProducto: [
            this.idProducto, 
            Validators.required],  
          nombreProducto : [{
            value: '',
            disabled: true
          }],

          tipoMovimiento: ["", [
              Validators.required,
              Validators.minLength(1),
            ]],
          cantidad:  [1, [
              Validators.required,
              Validators.min(1)
            ]],
          observaciones:  ["", [
              Validators.required,
            ]],
          fechaMovimiento:  ["", []]
    
  });

  }

  cargaProducto(): void {
  // Consume el servicio para obtener el producto ID
  this.productoService.obtenerProducto(this.idProducto).subscribe({

    next:(producto: Producto) => {

      this.formMovimientoInventario.patchValue({
        nombreProducto: producto.nombreProducto
      });
    },
    error:(err)=> {

      console.error("Error cargando producto", err);
      alert('Producto no encontrado');
      this.router.navigate(['/registrarMovimiento', this.idProducto]);
    } 
  })

}
  
  // Envia el formulario al bakend

  onSubmit():void{
    if(this.formMovimientoInventario.invalid){
      this.formMovimientoInventario.markAllAsTouched();
    return;
  }

  const movimiento = this.formMovimientoInventario.value;

  this.movimientoInventarioService
  .RegistrarMovimientoInventario(movimiento)
  .subscribe({
    next:()=> {
      this.formMovimientoInventario.reset({
        idProducto: this.idProducto
      });
    },
    error: err => {
      console.error('Error al registrar movimiento', err);
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

