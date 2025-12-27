import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { EcommerceValidador } from '../../Validador/ecommerce-validador';
import { RolService } from '../../../services/rol.service';
import { EstadoUsuarioService } from '../../../services/estadoUsuario.service';

@Component({
  selector: 'app-crear-usuario',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './crear-usuario.component.html',
  styleUrls: ['./crear-usuario.component.css']
})
export class CrearUsuarioComponent implements OnInit {

  formUsuarioPersona!: FormGroup;
  roles: any[] = [];
  estadosUsuario: any[] = [];

  archivoSeleccionado: File | null= null;

  constructor(
    private formBuilder: FormBuilder, // inyeccion de dependencia para construir formularios reactivos.
    private usuarioService: UsuarioService,
    private rolService: RolService,
    private estadoUsuarioService: EstadoUsuarioService,
  ) { }

  ngOnInit(): void {
    // Inicializar el formulario con grupo y campos animados
    this.inicializarForm();
    // llama al servicio para obtener rol
    this.cargarRoles();
    // llama al servicio para obtener estado
    this.cargarEstadoUsuario();
  }

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
      contrasenia: ["", [
        Validators.required,
        Validators.minLength(3),
        EcommerceValidador.noVacioConEspacios
      ]],
      fechaRegistro: ["", []],

      idRol: [null, Validators.required],
      idEstado: [null, Validators.required],

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

      archivo: [null],

    })
  }

  private cargarRoles(): void {
    this.rolService.obtenerRoles().subscribe(data => {
      this.roles = data;
    });
  }

  private cargarEstadoUsuario(): void {
    this.estadoUsuarioService.obtenerEstadosUsuario().subscribe(data => {
      this.estadosUsuario = data;
    }

    )
  }

  onFileSelected(event: any){
    this.archivoSeleccionado = event.target.files[0];
  }
  

  onSubmit() {

    if (this.formUsuarioPersona.invalid) {
      console.error('Formulario invalido')
      return;
    }

    const rawValues = this.formUsuarioPersona.value;
    const formData = new FormData();

   

      formData.append("nombreUsuario", rawValues.nombreUsuario);
      formData.append("correo",rawValues.correo);
      formData.append("contrasenia",rawValues.contrasenia);

      // Fecha Registro
      formData.append("fechaRegistro", rawValues.fechaRegistro);
      
    
      //Rol
      formData.append("idRol", rawValues.idRol);
      if(rawValues.idRol){
        formData.append("nombreRol", rawValues.nombreRol)
      }

      //Estado
      formData.append("idEstado", rawValues.idEstado);
      if(rawValues.idEstado){
        formData.append("nombreEstado", rawValues.nombreEstado)
      }


      formData.append("nombresPersona", rawValues.nombresPersona);
      formData.append("apellidosPersona", rawValues.apellidosPersona);
      formData.append("tipoDocumento", rawValues.tipoDocumento);
      formData.append("numeroDocumento", rawValues.numeroDocumento);
      
      if(rawValues.fechaNacimiento){
        formData.append("fechaNacimiento", rawValues.fechaNacimiento)
      }

      formData.append("sexo", rawValues.sexo);
      formData.append("telefono", rawValues.telefono);
      formData.append("correoSecundario", rawValues.correoSecundario);
      formData.append("direccion", rawValues.direccion);
      formData.append("ciudad", rawValues.ciudad);
      formData.append("pais", rawValues.pais);
   

    //Archivo (opcional)

    if(this.archivoSeleccionado){
      formData.append("archivo", this.archivoSeleccionado)
      formData.append("tipo", "USUARIO")
    }



    this.usuarioService.crearUsuario(formData).subscribe({
      next: () => {
        console.log("Usuario creado con exito");
        this.formUsuarioPersona.reset();
      },
      error: err => {
        console.error("Error al crear producto", err)
      }
    });

  }

}
