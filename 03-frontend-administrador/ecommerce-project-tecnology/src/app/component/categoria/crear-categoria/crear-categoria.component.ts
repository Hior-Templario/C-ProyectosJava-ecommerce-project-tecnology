import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CategoriaService } from '../../../services/categoria.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-crear-categoria',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './crear-categoria.component.html',
  styleUrls: ['./crear-categoria.component.css']
})
export class CrearCategoriaComponent implements OnInit{

  formCategoria!: FormGroup;

  archivoSeleccionado: File | null= null;

  constructor(
    private formBuilder : FormBuilder, // Inyeccion de dependencia para contruir formulario reactivo
    private categoriaService: CategoriaService, // Inyeccion del servicio para manejar la logica de categoria
    private router: Router // Router de Angular para navegacoón programática

  ){}

  
  // Metodo del ciclo de vida : se ejecuta al inicializar el componente
  ngOnInit(): void {
    
    // Inicializar el formulario con grupos y campos aninados
    this.inicializarForm();
  }

  private inicializarForm(): void {
    this.formCategoria = this.formBuilder.group({
      nombreCategoria: ["", [
        Validators.required,
        Validators.minLength(2),
        EcommerceValidador.noVacioConEspacios
      ]],
      descripcion: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],
      prefijoCategoria: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],
      archivo: [null],

    })
  }

  onFileSelect(event: any){
    this.archivoSeleccionado = event.target.files[0];
  }

  onSubmit(){

    if(this.formCategoria.invalid){
      console.error('Formulario invalido')
      return;
    }

    const rawValues = this.formCategoria.value;
    const formData = new FormData();

      formData.append("nombreCategoria", rawValues.nombreCategoria);
      formData.append("descripcion",rawValues.descripcion);
      formData.append("prefijoCategoria", rawValues.prefijoCategoria)
      
      // Archivo (Opcional)

      if(this.archivoSeleccionado){
        formData.append("archivo", this.archivoSeleccionado)
        formData.append("tipo", "CATEGORIA")
      }
      
      
    // llama el servicio para crear el producto en el backend
      this.categoriaService.crearCategoria(formData).subscribe({
        next: (resp:any) => {
          console.log("Categorida creada con exito");
          const id = resp.idCategoria; //Obtiene el id de la categoria creado
          this.formCategoria.reset()
        },
        error: err => {
          console.error("Error al crear categoria", err)
        }
      })

  }

}


