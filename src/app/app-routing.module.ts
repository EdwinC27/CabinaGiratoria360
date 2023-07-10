import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuAccionesComponent } from './menu-acciones/menu-acciones.component';

const routes: Routes = [
  {
    path: "",
    component: MenuAccionesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }