import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MenuAccionesComponent } from './menu-acciones/menu-acciones.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms'; // Importa FormsModule

@NgModule({
  declarations: [
    AppComponent,
    MenuAccionesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule ,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
