import { Routes } from '@angular/router';

import { ListaCategoriaComponent } from './component/categoria/lista-categorias/lista-categoria.component';
import { AgregarProductoComponent } from './component/producto/agregar-producto/agregar-producto.component';
import { DashboardComponent } from './component/producto/dashboard/dashboard/dashboard.component';
import { ListaUsuariosComponent } from './component/usuario/lista-usuarios/lista-usuarios.component';
import { ListaProductoComponent } from './component/producto/lista-productos/lista-productos.component';
import { LoginComponent } from './component/usuario/login-component/login-component.component';
import { PerfilUsuarioComponent } from './component/usuario/perfil-usuario/perfil-usuario.component';
import { CrearUsuarioComponent } from './component/usuario/crear-usuario/crear-usuario.component';
import { CrearCategoriaComponent } from './component/categoria/crear-categoria/crear-categoria.component';
import { DetallesProductoComponent } from './component/producto/detalles-producto/detalles-producto.component';
import { CrearMarcaComponent } from './component/marca/crear-marca/crear-marca.component';
import { ListaMarcaComponent } from './component/marca/lista-marca/lista-marca.component';
import { DetallesMarcaComponent } from './component/marca/detalles-marca/detalles-marca.component';
import { RegistrarMovimientoComponent } from './component/inventario/registrar-movimiento/registrar-movimiento.component';
import { ListarInventarioComponent } from './component/inventario/listar-inventario/listar-inventario.component';
import { EditarProductoComponent } from './component/producto/editar-producto/editar-producto.component';
import { EditarUsuarioComponent } from './component/usuario/editar-usuario/editar-usuario/editar-usuario.component';

export const routes: Routes = [
    {   path: 'dashboard', component: DashboardComponent },

    {   path: 'listaProductos', component: ListaProductoComponent }, 

    {   path: 'agregarProducto',  component: AgregarProductoComponent},

    {   path: 'modificarProducto/:idProducto', component: EditarProductoComponent},

    {   path: 'detallesProducto/:idProducto', component: DetallesProductoComponent},

    {   path: 'listarInventario', component: ListarInventarioComponent }, 
    
    {   path: 'registrarMovimiento', component: RegistrarMovimientoComponent},

    {   path: 'listaCategorias', component: ListaCategoriaComponent },

    {   path: 'crearCategoria', component: CrearCategoriaComponent},

    {   path: 'listaMarca', component: ListaMarcaComponent},

    {   path: 'crearMarca', component: CrearMarcaComponent},

    {   path: 'detallesMarca/:idMarca', component: DetallesMarcaComponent},
    
    {   path: 'listaUsuarios', component: ListaUsuariosComponent },
    
    {   path: 'crearUsuarios',  component: CrearUsuarioComponent},

    {   path: 'modificarUsuario/:idUsuario', component: EditarUsuarioComponent},

    {   path: 'login', component: LoginComponent}, 
    
    {   path: 'perfilUsuario/:idUsuario', component: PerfilUsuarioComponent}, 

    {   path: '',  redirectTo: '/dashboard' , pathMatch: 'full' },

    {   path: '**',  redirectTo: '/dashboard' , pathMatch: 'full' }

];


 