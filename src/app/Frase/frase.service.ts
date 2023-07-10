import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FraseService {
  fraseCompartida: string = '';

  constructor() { }

  establecerFraseCompartida(frase: string) {
    this.fraseCompartida = frase;
  }

  obtenerFraseCompartida() {
    return this.fraseCompartida;
  }
}