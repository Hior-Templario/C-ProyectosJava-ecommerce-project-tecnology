import { Component, Input } from '@angular/core';
import { ImagenService } from '../../../services/imagen.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { error, log } from 'console';

@Component({
  selector: 'app-cargar-imagen',
  imports: [CommonModule],
  templateUrl: './cargar-imagen.component.html',
  styleUrls: ['./cargar-imagen.component.css']
})
export class CargarImagenComponent {

  @Input() tipo!: string;
  @Input() idEntidad!: number;

  ngOnInit(){
    console.log('INIT tipo:' , this.tipo);
    console.log('INIT idEntidad;' , this.idEntidad);
  }

  archivosSeleccionados: File[] = [];

  constructor(private imagenService: ImagenService) { }

  onFileSelected(event: any) {
    this.archivosSeleccionados = Array.from(event.target.files);
    console.log('Archivos:', this.archivosSeleccionados);

  }

  RemplazarImagenes(): void {

    console.log('ARCHIVOS ACTUALES', this.archivosSeleccionados)
    console.log('Click en remplazar imágenes');
    console.log('DEBUG tipoEntidad:' , this.tipo);
    console.log('DEBUG idEntidad:', this.idEntidad);

    if (!this.idEntidad ) {
      console.log('idEntidad no definidos')
      return;
    }

    if (this.archivosSeleccionados.length === 0) {
      console.warn('No hay archivos seleccionados')
      return;
    }


    // Eliminar imagenes acturales
    this.imagenService.
      eliminarPorEntidad(this.tipo, this.idEntidad)
      .subscribe({
        next: () => {

          // subir nuevas imagenes
          this.archivosSeleccionados.forEach(archivo => {
            this.imagenService
              .subirImagen(archivo, this.tipo, this.idEntidad)
              .subscribe({
                next: () => console.log('Imagen subida:', archivo.name),
                error: err => console.error('Error subiendo imagen', err)

              });

          });

        },

        error: err => console.error("Error elimando imágeens", err)

      });
  }
}