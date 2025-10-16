import { Component } from '@angular/core';
import { Producto } from '../../../models/producto';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { CommonModule } from '@angular/common';
import { Marca } from '../../../models/marca';
import { Categoria } from '../../../models/categoria';
import { MarcaService } from '../../../services/marca.service';
import { CategoriaService } from '../../../services/categoria.service';

@Component({
  selector: 'app-detalles-producto',
  imports: [CommonModule, RouterModule],
  templateUrl: './detalles-producto.component.html',
  styleUrls: ['./detalles-producto.component.css']//Estilo CSS
})
export class DetallesProductoComponent {

  producto!: Producto;

  constructor(
    private route : ActivatedRoute,
    private productoService : ProductoService,
  
  ){}

  ngOnInit(): void{

    const idProducto = Number(this.route.snapshot.paramMap.get('idProducto'))
    
    if(idProducto){
      
      this.productoService.obtenerProducto(idProducto).subscribe({
      next:(data) =>{
        console.log("Datos de los detalles", data);
        this.producto = data;
      },
    });
  }
}
}
