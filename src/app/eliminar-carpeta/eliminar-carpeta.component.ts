import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { NombreFiestaService } from '../NombreFiesta/Nombre.Fiesta.Service'
import { PeticionEliminarCarpeta } from '../Peticiones-API/EliminarCarpeta/eliminar';

@Component({
  selector: 'app-eliminar-carpeta',
  templateUrl: './eliminar-carpeta.component.html',
  styleUrls: ['./eliminar-carpeta.component.css']
})
export class EliminarCarpetaComponent {
  inputText: string = '';
  textnombreFiesta = this.nombreFiesta.obtenerNombreFiesta();
  responseData: any;
  mensajeEliminacion: string = "";

  constructor(private nombreFiesta: NombreFiestaService, private router: Router, private peticionEliminarCarpeta: PeticionEliminarCarpeta, private cdr: ChangeDetectorRef) { }

  eliminar() {
    this.peticionEliminarCarpeta.getEliminarFiesta(this.textnombreFiesta).subscribe((message) => {
      this.responseData = message;
      if (this.responseData == "Carpeta eliminada correctamente") {
        this.mensajeEliminacion = "Evento eliminado correctamente";
        this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
      }
    }, (error) => {
      this.mensajeEliminacion = error;
      this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
    });
  }

  regresar() {
    this.router.navigate(['/']);
  }
}
