// Importaciones básicas Angular
import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { Rol } from '../../../models/rol';
import { UsuarioService } from '../../../services/usuario.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';


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

//Inyeccion de dependencias
constructor(
    private  usuarioService : UsuarioService){}


// Metodo del ciclo de vida: se ejecuta al inicializar el componente
ngOnInit(): void{
  this.cargarDatos();
}

// Carga todos los datos necesarios
  cargarDatos() {
   //Carga Usuarios
   this.usuarioService.obtenerUsuarios().subscribe({
    next:(usuarios) => {
      console.log("Usuarios recibidos desde componente Usuarios", usuarios)
      this.usuarios = usuarios;
    }
   })
  }
}


