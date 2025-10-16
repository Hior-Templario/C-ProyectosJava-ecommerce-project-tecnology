import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MarcaService } from '../../../services/marca.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-crear-marca',
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './crear-marca.component.html',
  styleUrls:[ './crear-marca.component.css']
})
export class CrearMarcaComponent implements OnInit {


  formMarca!: FormGroup; //Grupo de formularios reactivos.

  archivoSeleccionado: File | null = null;

  constructor(
    private formBuilder: FormBuilder, // Inyeccion de dependencia para contruir formulario reactivo
    private marcaService: MarcaService // Inyeccion del servicio para manejar la logica de marca

  ) { }


  // Metodo del ciclo de vida : se ejecuta al inicializar el componente
  ngOnInit(): void {

    // Inicializa el formulario con grupos y campos aninados
    this.inicializarForm();

  }

  private inicializarForm(): void {
    this.formMarca = this.formBuilder.group({
      nombreMarca: ["", [
        Validators.required,
        Validators.minLength(2),
        EcommerceValidador.noVacioConEspacios
      ]],
      descripcion: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],
      archivo: [null],

    })
  }

  onFileSelect(event: any) {

    this.archivoSeleccionado = event.target.files[0];
  }

  onSubmit() {

    if (this.formMarca.invalid) {
      console.error('Formulario invalido')
      return
    }

    const rawValues = this.formMarca.value
    const formData = new FormData();

    formData.append("nombreMarca", rawValues.nombreMarca)
    formData.append("descripcion", rawValues.descripcion)

    // Archivo opcional

    if (this.archivoSeleccionado) {
      formData.append("archivo", this.archivoSeleccionado)
      formData.append("tipo", "MARCA")

    }

    this.marcaService.crearMarca(formData).subscribe({
      next: () => {
        console.log("Marca creada con exito");
        this.formMarca.reset()
      },
      error: err => {
        console.error("Error al crear Marca", err)
      }
    })
  }
}