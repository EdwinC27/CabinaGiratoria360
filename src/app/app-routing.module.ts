import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EliminarCarpetaComponent } from './eliminar-carpeta/eliminar-carpeta.component';
import { MenuAccionesComponent } from './menu-acciones/menu-acciones.component';

const routes: Routes = [
  {
    path: "",
    component: MenuAccionesComponent
  },
  {
    path: "eliminar",
    component: EliminarCarpetaComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
