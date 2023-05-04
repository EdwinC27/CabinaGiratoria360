import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SeleccionarFiestaComponent } from './seleccionar-fiesta/seleccionar-fiesta.component';

const routes: Routes = [
  {
    path: "",
    component: SeleccionarFiestaComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
