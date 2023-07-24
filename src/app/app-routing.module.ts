import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccederCarpetaComponent } from './acceder-carpeta/acceder-carpeta.component';
import { EliminarCarpetaComponent } from './eliminar-carpeta/eliminar-carpeta.component';
import { AuthGuard } from './login/auth/auth.guard';
import { LoginComponent } from './login/login.component';
import { MenuAccionesComponent } from './menu-acciones/menu-acciones.component';

const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "menu",
    component: MenuAccionesComponent,
  },
  {
    path: "eliminar",
    component: EliminarCarpetaComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "acceder",
    component: AccederCarpetaComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
