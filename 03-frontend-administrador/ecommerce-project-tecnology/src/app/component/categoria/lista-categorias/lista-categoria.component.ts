import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Categoria } from '../../../models/categoria';

import { CategoriaService } from '../../../services/categoria.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-lista-categoria',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lista-categoria.component.html',
  styleUrls: ['./lista-categoria.component.css']
})

 export class ListaCategoriaComponent implements OnInit {

   categorias : Categoria[]=[];

   constructor(

    private categoriaService : CategoriaService
   ){}

   ngOnInit(): void {
    this.cargarDatos();
   }


   cargarDatos():void {

     // Carga categorias ( con log para debugging)
     this.categoriaService.obtenerCategorias().subscribe({
       next: (categorias) => {
        console.log("Categorias recibidas desde componente categorias", categorias)
        this.categorias = categorias;
      },
      error: (err) => console.error('error cargando productos:' , err)
     })
   }
 }


