import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MenuAccionesComponent } from './menu-acciones/menu-acciones.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { EliminarCarpetaComponent } from './eliminar-carpeta/eliminar-carpeta.component';
import { HttpClientModule } from '@angular/common/http';
import { AccederCarpetaComponent } from './acceder-carpeta/acceder-carpeta.component';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuAccionesComponent,
    EliminarCarpetaComponent,
    AccederCarpetaComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule ,
    FormsModule,
    HttpClientModule,
    CarouselModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
