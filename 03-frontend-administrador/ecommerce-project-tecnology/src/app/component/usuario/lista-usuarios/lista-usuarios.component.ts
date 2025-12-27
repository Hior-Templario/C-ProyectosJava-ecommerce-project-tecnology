// Importaciones básicas Angular
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { Rol } from '../../../models/rol';
import { UsuarioService } from '../../../services/usuario.service';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UsuarioPersonaAdapter } from '../../../models/usuario-persona-adapter';
import { errorContext } from 'rxjs/internal/util/errorContext';


@Component({
  selector: 'app-lista-usuarios',//Selector para usar en plantillas HTML
  standalone:true, // Componente autonomo (no necesita modulo padre)
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './lista-usuarios.component.html',//plantilla asociada
  styleUrls: ['./lista-usuarios.component.css']//Estilo CSS
})
export class ListaUsuariosComponent implements OnInit {
// Propiedades del component
usuarios: Usuario[]=[]; // Lista de usuarios a mostrar
roles : Rol[]=[]; // Lista de roles a mostrar
rolSeleccionado: number| null = null; //ID de rol seleccionado
isBrowser: boolean;


paginaActual =0;
tamanioPagina = 10;
totalPaginas = 0;

//Inyeccion de dependencias
constructor(
    private  usuarioService : UsuarioService,
    @Inject(PLATFORM_ID) private platformId:Object

  
  ){
    // Detecta si estamos en navegador o SSR
        this.isBrowser = isPlatformBrowser(this.platformId)
  }
  


// Metodo del ciclo de vida: se ejecuta al inicializar el componente
ngOnInit(): void{
  this.cargarDatos();
}

// Carga todos los datos necesarios
  cargarDatos() {
   //Carga Usuarios
   this.usuarioService.obtenerUsuariosPersonasPaginados(this.paginaActual, this.tamanioPagina).subscribe({
    next:(response) => {
      console.log("paginas recibidas", response)
      this.usuarios = response.content.map((item: any) => this.usuarioService.adapter.adapt(item))
      this.totalPaginas = response.totalPages;
    },
    error:(err) => console.error('error cargando usuarios', err)
   })
  }


  cambiarPagina(nuevaPagina: number){
    if(nuevaPagina >=0 && nuevaPagina < this.totalPaginas){
      this.paginaActual = nuevaPagina;
      this.cargarDatos()
    }
  }
}


