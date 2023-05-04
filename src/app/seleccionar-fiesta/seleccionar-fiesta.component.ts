import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-seleccionar-fiesta',
  templateUrl: './seleccionar-fiesta.component.html',
  styleUrls: ['./seleccionar-fiesta.component.css']
})
export class SeleccionarFiestaComponent {
  inputText: string = '';
  botonHabilitado: boolean = false;
  
  constructor(private router: Router) {}

  buscarQR() {
    if(parseInt(this.inputText) <= 4 && parseInt(this.inputText) >= 1) {
      this.router.navigate(['/ruta-del-otro-componente']);
      alert(this.inputText)
    } else {
      alert("Número de fiesta inválido");
    }
  }
}
