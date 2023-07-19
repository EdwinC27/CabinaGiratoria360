import { Component, ChangeDetectorRef, Renderer2 } from '@angular/core';
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

  isPopupVisible: boolean = false;

  constructor(private nombreFiesta: NombreFiestaService, private router: Router, private peticionEliminarCarpeta: PeticionEliminarCarpeta, private cdr: ChangeDetectorRef,  private renderer: Renderer2) { }

  eliminar() {
    this.peticionEliminarCarpeta.getEliminarFiesta(this.textnombreFiesta).subscribe((message) => {
      this.responseData = message;
      if (this.responseData == "Carpeta eliminada correctamente") {
        this.showPopup("Evento eliminado correctamente");

        this.mensajeEliminacion = "Evento eliminado correctamente";
        this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
      }
    }, (error) => {
      this.showPopup(error);

      this.mensajeEliminacion = error;
      this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
    });
  }

  regresar() {
    this.router.navigate(['/']);
  }

  showPopup(message: string) {
    const popupContainer = this.renderer.createElement('div');
    const popupText = this.renderer.createText(message);

    this.renderer.appendChild(popupContainer, popupText);
    this.renderer.setAttribute(popupContainer, 'id', 'popupContainer');

    this.renderer.listen(popupContainer, 'click', () => {
      this.hidePopup(popupContainer);
    });

    this.renderer.appendChild(document.body, popupContainer);
    this.cdr.detectChanges();

    this.isPopupVisible = true;

    setTimeout(() => {
      this.hidePopup(popupContainer);
    }, 3000);
  }

  hidePopup(popupContainer: any) {
    this.renderer.removeChild(document.body, popupContainer);
    this.cdr.detectChanges();

    this.isPopupVisible = false;
  }
}