import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../../../services/usuario.service';
import { EcommerceValidador } from '../../../Validador/ecommerce-validador';
import { Usuario } from '../../../../models/usuario';
import { UsuarioActualizacion } from '../../../../models/usuarioActualizacion';

@Component({
  selector: 'app-editar-usuario',
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './editar-usuario.component.html',
  styleUrl: './editar-usuario.component.css'
})
export class EditarUsuarioComponent {

  formUsuarioPersona!: FormGroup;  //Grupo de formularios reactivos
  idUsuario!: number //Almacena el ID del usuario a editar PROVENIENTE DE LA RUTA

  constructor (
    private formBuilder: FormBuilder, // inyeccion de FormBuilder para countruir formularios reactivos
    private usuarioService: UsuarioService, // Servicio para obtener usuarios
    private router : Router, // Router de Angular para navegación prográmatica
    private route : ActivatedRoute, // Inyeccion de ActivateRoute para obtener parametros de la ruta

  ){ }

  ngOnInit():void{
    // Obtiene id de Url
    this.idUsuario = Number(this.route.snapshot.paramMap.get('idUsuario'));
   // console.info('este es el id usuario: ' + this.idUsuario);
    // Valida que el ID sea vàlido antes de continuar
    if(!this.idUsuario || this.idUsuario <=0 ){
      console.error('ID de usuario invàlido');
      this.router.navigate(['/listaUsuarios'])
      return;
    }
    // Incializa el formulario con grupos y campos anidados
    this.inicializarForm();
    this.cargarUsuario();
    }
    // llama al servicio para  obtener producto


private inicializarForm(): void {
    this.formUsuarioPersona = this.formBuilder.group({
      nombreUsuario: ["", [
        Validators.required,
        Validators.minLength(2),
        EcommerceValidador.noVacioConEspacios
      ]],
      correo: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      idRol:["", []],
    
      nombreRol: ["", [
        Validators.required,
        Validators.minLength(2),
        EcommerceValidador.noVacioConEspacios
      ]],
      
      idEstado:["", []],

      nombreEstado: [null, Validators.required],

      persona: this.formBuilder.group({

      idPersona:["", []],

      nombresPersona: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      apellidosPersona: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      tipoDocumento: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      numeroDocumento: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      fechaNacimiento: ["", []],

      sexo: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      telefono: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      correoSecundario: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      direccion: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      ciudad: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],

      pais: ["", [
        Validators.required,
        EcommerceValidador.noVacioConEspacios
      ]],
      })

    });
  }

  cargarUsuario(): void {
    // Consume el servicio para obtener el producto ID
    this.usuarioService.obtenerUsuarioPersona(this.idUsuario).subscribe({
      next:(usuario : Usuario) => {
        // Carga los valores del producto en el formulario
        this.formUsuarioPersona.patchValue({
          nombreUsuario: usuario.nombreUsuario,
          correo: usuario.correo,
          idRol: usuario.rol.idRol,
          nombreRol: usuario.rol.nombreRol,
          idEstado: usuario.estadoUsuario.idEstado,
          nombreEstado: usuario.estadoUsuario.nombreEstado,
          persona: {
                idPersona: usuario.persona.idPersona,
                nombresPersona: usuario.persona.nombresPersona,
                apellidosPersona: usuario.persona.apellidosPersona,
                tipoDocumento: usuario.persona.tipoDocumento,
                numeroDocumento: usuario.persona.numeroDocumento,
                fechaNacimiento: usuario.persona.fechaNacimiento,
                sexo: usuario.persona.sexo,
                telefono: usuario.persona.telefono,
                correoSecundario: usuario.persona.correoSecundario,
                direccion: usuario.persona.direccion,
                ciudad: usuario.persona.ciudad,
                pais: usuario.persona.pais,
          },
        });
      },
      error:(err) =>{
        // Manejo del error si el usuario no existe o falla la periciòn
        console.error("Error cargando usuario", err);
        alert('Usuario no encontrado');
        this.router.navigate(['/listaUsuarios']);
      }
    })

  }

  actualizarUsuario(): void {
    // construye el objeto actualizacion a partir del formulario
    const usuarioActualizacion: UsuarioActualizacion = this.formUsuarioPersona.value;

    // Envia la solicitud de acrualizacion al backend
    this.usuarioService.actualizarUsuario(this.idUsuario, usuarioActualizacion).subscribe({

      
    })


}

}
