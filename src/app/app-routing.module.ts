import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccederCarpetaComponent } from './acceder-carpeta/acceder-carpeta.component';

const routes: Routes = [
  {
    path: "evento/:evento/usuario/:usuario",
    component: AccederCarpetaComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
