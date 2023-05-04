import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SeleccionarFiestaComponent } from './seleccionar-fiesta/seleccionar-fiesta.component';
import { FormsModule } from '@angular/forms';
import { QrFiestaComponent } from './qr-fiesta/qr-fiesta.component';

@NgModule({
  declarations: [
    AppComponent,
    SeleccionarFiestaComponent,
    QrFiestaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
