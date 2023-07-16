import { Component, ChangeDetectorRef, Renderer2 } from '@angular/core';
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

  isPopupVisible: boolean = false;

  constructor(private nombreFiesta: NombreFiestaService, private router: Router, private peticionCrearCarpeta: PeticionCrearCarpeta, private cdr: ChangeDetectorRef,  private renderer: Renderer2) { }

  crear() {
    this.peticionCrearCarpeta.getCrearFiesta(this.textnombreFiesta).subscribe((message) => {
      this.responseData = message;
      if (this.responseData == "Carpeta creada correctamente") {
        this.showPopup("Evento creado correctamente");

        this.mensajeEliminacion = "Evento creado correctamente";
        this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
      }
    }, (error) => {
      this.mensajeEliminacion = error;
      this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
    });
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
    }, 5000);
  }

  hidePopup(popupContainer: any) {
    this.renderer.removeChild(document.body, popupContainer);
    this.cdr.detectChanges();

    this.isPopupVisible = false;
  }

  regresar() {
    this.router.navigate(['/']);
  }
}
