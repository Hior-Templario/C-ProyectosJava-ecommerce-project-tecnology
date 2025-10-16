import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../../services/producto.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { Router, RouterModule } from '@angular/router';
import { CategoriaService } from '../../../services/categoria.service';
import { MarcaService } from '../../../services/marca.service';

@Component({
  selector: 'app-agregar-producto', //Selector para utilizar en plantillas HTMLL
  standalone: true, // coponente autonomo (no necesita modulo padre)
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './agregar-producto.component.html', //Plantilla html asociada
  styleUrls: ['./agregar-producto.component.css'] //Estilo CSS asociados
})

export class AgregarProductoComponent implements OnInit {
  formProducto!: FormGroup; //Grupo de formularios reactivos.
  categorias: any[] = []; // Arreglo que obtendra las categorias obtenidas de servicio
  marcas: any[] = []; // Arreglo que obtendra las marcas obtenidas de servicio

  archivoSeleccionado: File[] = []; // Arreglo para almacenar los archivos de imagenes seleccionadas por el usuario

  constructor(
    private formBuilder: FormBuilder, // inyeccion de FormBuilder para construir formularios reactivos.
    private productoService: ProductoService, // Servicio para obtener productos
    private categoriaServicio: CategoriaService, //Servicio para obtener categorias
    private marcaServicio: MarcaService, // Servicio para obtener marcas
    private router: Router // Router de Angular para navegacoón programática

  ) { }

  //Metodo del ciclo de vida: se ejecuta al inicializar el componente
  ngOnInit(): void {

    // Inicializa el formulario con grupos y campos aninados
    this.inicializarForm();
    // llama al servicio para obtener caregorias
    this.cargarCategoras();
    // llama al servicio para obtener marcas
    this.cargarMarcas();

  }

  // Método privado para inicializar el formulario reactico
  private inicializarForm(): void {
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
      fechaRegistro: ["", []],
      fechaActualizacion: ["", []],

      idCategoria: [null, Validators.required],

      idMarca: [null, Validators.required],

      archivo: [null],

    });
  }

  // Método para cargar categorías usando el servicio
  private cargarCategoras(): void {
    this.categoriaServicio.obtenerCategorias().subscribe(data => {
      this.categorias = data;
    });
  };

  // Método para cargar matcas usando el servicio

  private cargarMarcas(): void {
    this.marcaServicio.obtenerMarcas().subscribe(data => {
      this.marcas = data;
    });
  }

  // Método para caputurar archivos selecccionados desde el input de tipo file
  onFileSelected(event: any) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.archivoSeleccionado = Array.from(input.files as FileList);
      console.log("Archivos seleccionados", this.archivoSeleccionado)
    }
  }

  // Método para enviar el formulario y crear producro
  onSubmit() {

    if (this.formProducto.invalid) { //Valida que el formulario sea válido
      console.error('Formulario invallido');
      return; // Si no es valido, no continua
    }

    const rawValues = this.formProducto.value; // Obtiene los valores del formulario
    const formData = new FormData(); // Crea un FormData para enviar datos y archivos

    // Agregar los campos del formulario al FromData
    formData.append("nombreProducto", rawValues.nombreProducto);
    formData.append("descripcion", rawValues.descripcion);
    formData.append("precio", rawValues.precio);

    formData.append("fechaRegistro", rawValues.fechaRegistro);
    formData.append("fechaActualizacion", rawValues.fechaActualizacion);

    formData.append("idCategoria", rawValues.idCategoria);

    // Opcional: si se tiene nombre de la categoria
    if (rawValues.idCategoria) {
      formData.append("nombreCategoria", rawValues.nombreCategoria)
    }

    formData.append("idMarca", rawValues.idMarca);

    // Opcional: si se tiene nombre de la marca
    if (rawValues.idMarca) {
      formData.append("nombreMarca", rawValues.nombreMarca)
    }

    // Agregar archivos seleccionados al FormData
    if (this.archivoSeleccionado && this.archivoSeleccionado.length > 0) {

      this.archivoSeleccionado.forEach((file) => {
        formData.append("archivo", file);
      });
    }

    // llama el servicio para crear el producto en el backend
    this.productoService.crearProducto(formData).subscribe({
      next: (resp: any) => {
        console.log("Producto creado con exito");
        const id = resp.idProducto; //Obtiene el id del producto creado
        this.router.navigate(['/detallesProducto', id]) // Redirige a la pagina de detalle
      },
      error: err => {
        console.error("Error al guardar producto", err)
      }
    });

  }
}