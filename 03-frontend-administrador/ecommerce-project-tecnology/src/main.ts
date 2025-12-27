import { bootstrapApplication } from "@angular/platform-browser"; // Funcion para arrancar la app sin NgModules
import { AppComponent } from "./app/app.component"; // Componente central de la aplicación
import { provideHttpClient, withInterceptors } from "@angular/common/http"; // Proveedor para habilitar el uso de HttpClient
import { provideRouter, Routes } from "@angular/router"; // Proceedor y tipo para configurarrutas
import { ListaProductoComponent } from "./app/component/producto/lista-productos/lista-productos.component"; // Componente para listar productos
import { ListaCategoriaComponent } from "./app/component/categoria/lista-categorias/lista-categoria.component"; // Componente para listar Categorias
import { DashboardComponent } from "./app/component/producto/dashboard/dashboard/dashboard.component";
import { ListaUsuariosComponent } from "./app/component/usuario/lista-usuarios/lista-usuarios.component";
import { tokenInterceptor } from "./app/interceptors/token.interceptor";
import { LoginComponent } from "./app/component/usuario/login-component/login-component.component";
import { AgregarProductoComponent } from "./app/component/producto/agregar-producto/agregar-producto.component";
import { PerfilUsuarioComponent } from "./app/component/usuario/perfil-usuario/perfil-usuario.component";
import { CrearUsuarioComponent } from "./app/component/usuario/crear-usuario/crear-usuario.component";
import { CrearCategoriaComponent } from "./app/component/categoria/crear-categoria/crear-categoria.component";
import { DetallesProductoComponent } from "./app/component/producto/detalles-producto/detalles-producto.component";
import { CrearMarcaComponent } from "./app/component/marca/crear-marca/crear-marca.component";
import { ListaMarcaComponent } from "./app/component/marca/lista-marca/lista-marca.component";
import { DetallesMarcaComponent } from "./app/component/marca/detalles-marca/detalles-marca.component";
import { RegistrarMovimientoComponent } from "./app/component/inventario/registrar-movimiento/registrar-movimiento.component";
import { ListarInventarioComponent } from "./app/component/inventario/listar-inventario/listar-inventario.component";
import { provideAnimations } from "@angular/platform-browser/animations";
import { EditarProductoComponent } from "./app/component/producto/editar-producto/editar-producto.component";
import { EditarUsuarioComponent } from "./app/component/usuario/editar-usuario/editar-usuario/editar-usuario.component";

const routes: Routes = [ // Se definen las rutas disponibles en la app.

{   path: 'dashboard', component: DashboardComponent },
{   path: 'listaProductos', component: ListaProductoComponent }, 
{   path: 'agregarProducto',  component: AgregarProductoComponent},
{   path: 'modificarProducto/:idProducto', component: EditarProductoComponent},
{   path: 'detallesProducto/:idProducto', component: DetallesProductoComponent},
{   path: 'listarInventario', component: ListarInventarioComponent }, 
{   path: 'listaCategorias', component: ListaCategoriaComponent }, // Ruta ' /categorias' muestra listaCategoriaComponent
{   path: 'registrarMovimiento', component: RegistrarMovimientoComponent},
{   path: 'crearCategoria', component: CrearCategoriaComponent},
{   path: 'listaMarca', component: ListaMarcaComponent},
{   path: 'crearMarca', component: CrearMarcaComponent},
{   path: 'detallesMarca/:idMarca', component: DetallesMarcaComponent},
{   path: 'listaUsuarios', component: ListaUsuariosComponent }, // Ruta ' /usuarios' muestra listaCategoriaComponent
{   path: 'crearUsuarios',  component: CrearUsuarioComponent},
{   path: 'modificarUsuario/:idUsuario', component: EditarUsuarioComponent},
{   path: 'productos',  component: LoginComponent}, 
{   path: 'login', component: LoginComponent}, 
{   path: 'perfilUsuario/:idUsuario', component: PerfilUsuarioComponent}, 
{   path: '',  redirectTo: '/dashboard' , pathMatch: 'full' },
{   path: '**',  redirectTo: '/dashboard' , pathMatch: 'full' }

];

bootstrapApplication(AppComponent, { // Inicializa la app con AppComponent como componente raiz

    providers: [
      provideHttpClient(withInterceptors([tokenInterceptor])), 
      provideRouter(routes), // Se habilitan las rutas definidas arriba
      provideAnimations()
    ]
  }).catch(err => console.error(err)); // Captura errores de arranque y los muestra en consola
  
  