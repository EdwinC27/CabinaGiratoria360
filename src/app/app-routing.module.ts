import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SeleccionarFiestaComponent } from './seleccionar-fiesta/seleccionar-fiesta.component';
import { QrFiestaComponent } from './qr-fiesta/qr-fiesta.component';
import { MostrarFiestaComponent } from './mostrar-fiesta/mostrar-fiesta.component';

const routes: Routes = [
  {
    path: "",
    component: SeleccionarFiestaComponent
  },
  {
    path: "qr/:id",
    component: QrFiestaComponent
  },
  {
    path: "musica/:id",
    component: MostrarFiestaComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
