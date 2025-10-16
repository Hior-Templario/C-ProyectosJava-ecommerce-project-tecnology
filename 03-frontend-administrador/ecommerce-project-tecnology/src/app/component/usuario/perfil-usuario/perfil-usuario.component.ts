import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../../models/usuario';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/usuario.service';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-perfil-usuario',
  imports: [CommonModule, RouterModule],
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']//Estilo CSS
})
export class PerfilUsuarioComponent implements OnInit{

usuario!: Usuario;



  constructor(
        private route : ActivatedRoute,
        private  usuarioService : UsuarioService
  ){}
        
// Metodo del ciclo de vida: se ejecuta al inicializar el componente
ngOnInit(): void{
 
    const idUsuario =  Number(this.route.snapshot.paramMap.get('idUsuario'))
    this.usuarioService.obtenerUsuarioPersona(idUsuario).subscribe({
      next:(data) => {
         console.log("Datos del perfil", data)
         this.usuario = data; }
    });
}
}
