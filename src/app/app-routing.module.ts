import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccederCarpetaComponent } from './acceder-carpeta/acceder-carpeta.component';

const routes: Routes = [
  {
    path: "evento/:evento",
    component: AccederCarpetaComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
