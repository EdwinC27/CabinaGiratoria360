import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SeleccionarFiestaComponent } from './seleccionar-fiesta/seleccionar-fiesta.component';
import { FormsModule } from '@angular/forms';
import { QrFiestaComponent } from './qr-fiesta/qr-fiesta.component';
import { MostrarFiestaComponent } from './mostrar-fiesta/mostrar-fiesta.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    SeleccionarFiestaComponent,
    QrFiestaComponent,
    MostrarFiestaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
