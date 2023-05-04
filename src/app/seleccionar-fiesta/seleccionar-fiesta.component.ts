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
  id:any;
  
  constructor(private router: Router) {}

  buscarQR() {
    if(parseInt(this.inputText) <= 4 && parseInt(this.inputText) >= 1) {
      switch(parseInt(this.inputText)) {
        case 1:
          this.id = 1;
          break;

        case 2:
          this.id = 2;
          break;

        case 3:
          this.id = 3;
          break;

        case 4:
          this.id = 4;
          break;
      }

      this.router.navigate(['/qr', this.id]);
    } else {
      alert("Número de fiesta inválido");
    }
  }
}
