import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { NombreFiestaService } from '../NombreFiesta/Nombre.Fiesta.Service'
import { PeticionCrearCarpeta } from '../Peticiones-API/CrearCarpeta/crear';

@Component({
  selector: 'app-crear-carpeta',
  templateUrl: './crear-carpeta.component.html',
  styleUrls: ['./crear-carpeta.component.css']
})
export class CrearCarpetaComponent {
  inputText: string = '';
  textnombreFiesta = this.nombreFiesta.obtenerNombreFiesta();
  responseData: any;
  mensajeEliminacion: string = "";

  constructor(private nombreFiesta: NombreFiestaService, private router: Router, private peticionCrearCarpeta: PeticionCrearCarpeta, private cdr: ChangeDetectorRef) { }

  crear() {
    this.peticionCrearCarpeta.getCrearFiesta(this.textnombreFiesta).subscribe((message) => {
      this.responseData = message;
      if (this.responseData == "Carpeta creada correctamente") {
        this.mensajeEliminacion = this.responseData;
        this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
      }
    }, (error) => {
      this.mensajeEliminacion = error;
      this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
    });
  }
}
