
// Se importan los módulos y utilidades necesarias
import { Component } from '@angular/core'; // Decorador para definir un componente en Angular
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms'; //  Herramientas para crear formularios reactivos y validaciones
import { EcommerceValidador } from '../../Validador/ecommerce-validador'; // Validador personalizado para el formulario
import { AutenticacionService } from '../../../services/autenticacion.service'; // Servicio de autenticación para gestionar login y tokens
import { CommonModule } from '@angular/common'; // Módulo con directivas comunes de Angular (*ngIf, *ngFor, etc.)
import { RouterModule , Router } from '@angular/router'; // RouterModule para navegacion y Router para redirecciones

// Se define el decorador @Component para configurar este componente
@Component({
  selector: 'app-login-componen',  // Nombre de selector HTML para este componente
  imports: [CommonModule, RouterModule,ReactiveFormsModule], // Se importan módulos necesarios para el formulario y navegación
  templateUrl: './login-component.component.html', // Ruta al archivo de plantilla HTML
  styleUrls: ['./login-component.component.css'] // Estilos especificos para este componente
})

// Clase del componmente LoginComponent
export class LoginComponent {
  formLogin!: FormGroup; // Se declara el grupo de formularios reactivos para manejar el login


  // Se inyectan dependencias a través de constructor 
    constructor(
     private formBuilder: FormBuilder, // Servicio para construir formularios reactivos
     private autenticacionService: AutenticacionService, // Servicio que gestiona la autenticación
     private router: Router, // Router para redirigir tras login
  ){  }

  // Ciclo de vida ngOnInit; se ejecuta al inicializar el componente
  ngOnInit(): void {

      // se Inicializa el formulario con sus campos y validaciones
      this.inicializarForm();

  }

  // Método privado que inicializa los controles del formulario
    private inicializarForm():void{
    
      this.formLogin = this.formBuilder.group({
          nombreUsuario: ["", [
              Validators.required, // Campo requerido
              EcommerceValidador.noVacioConEspacios // Validador personalizado para evitar espacios vacíos
            ]],
          contrasenia: ["", [
              Validators.required, // Campo requerido
              Validators.minLength(2), // Minimo de 2 caracteres
            ]],
         
        });
    }

    // Método que se ejecuta al enviar el formulario
     onSubmit() {
      // Si el formulario no es válido, se muestra un error y no se envía
      if(this.formLogin.invalid){
        console.error('Formulario inválido');
        return;
      }

      // Se obtienen los valores del formulario
      const formData = this.formLogin.value;


      // Se llama al sevicio de autenticación para hacer login
      this.autenticacionService.login(
        formData.nombreUsuario,
        formData.contrasenia
      ).subscribe({
        next: (response) => {
          console.log("Respuesta login:", response);
          // Se almacena el token en el localStorage
          localStorage.setItem('token', response.token);
          
          // Se redirige al perfil del usuario autenticado
            this.router.navigate([`/perfilUsuario/${response.idUsuario}`])
      
          console.log("Login exitoso");
          // Se resetea el formulario
          this.formLogin.reset();
        },
        error: err => {
          // Se muestra el error en caso de fallo login
          console.error("Error en login", err)
        }
      });
  
  }

}
