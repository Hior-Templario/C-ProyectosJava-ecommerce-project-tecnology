// Se importan los módulos y utilidades necesarios desde Angular y otras librerias
import { CommonModule, isPlatformBrowser } from '@angular/common'; // CommonModule parra directivas básicas, isPlatformBrowser para detectar si se ejecuta en el navegador  
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core'; // Decorador Component, ciclo de vida OnInit y utilidades para inyección de dependencias
import { Router, RouterModule } from '@angular/router';  // RouterModule para navegacion
import { AutenticacionService } from './services/autenticacion.service'; // Servicio personalizado de autenticación


// Se define el decorador @Component para configurar este componente raíz de la aplicación
@Component({
  selector: 'app-root', // Nombre del selector HTML que representa este componente
  standalone: true, // Se indica que es un componente independiente (standalone)
  imports: [RouterModule, CommonModule], // Se importan módulos que el componente necesita
  templateUrl: './app.component.html', // Ruta al archivo HTML de la vista
  styleUrls: [ './app.component.css'] // Estilos específicos para el componente
})


// Se define la clase del componente principal (AppComponent)
export class AppComponent implements OnInit{
 
  idUsuario: number | null = null; // Se declara la variable idUsuario para guardar el identificador de usuario
  isBrowser: boolean; // Variable que indica si la aplicación se ejecuta en un navegador 
   
  //Se inyecta dependencias a través del constructor
  constructor(
    public autenticacionService: AutenticacionService,// servicio de autenticón  (maneja login/logout y estado del usuario)
    private router: Router, // Módulo de enrutamiento de Angular para navegar entre páginas
    @Inject(PLATFORM_ID) private platformId: Object
  ){
    // Se determina si el código está corriendo en un navegador
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  // Ciclo de vida ngOnInit: se ejecuta al inicializar el componente
  ngOnInit(){
    // Si el usuario ya está autenticado, se obtiene su id y se guarda en la variable idUsuario
    if(this.autenticacionService.isLoggedIn()){
    this.idUsuario = this.autenticacionService.getUsuarioId();
    }

  }

  // Método logout, cierra la sesión del usuario y redirige a la página de login
  logout(){
    this.autenticacionService.logout(); // Se cierra sesión
    this.router.navigate(['/login']); // Se redirige al login
  }
  

}
  
