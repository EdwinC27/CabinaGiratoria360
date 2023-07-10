import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NombreFiestaService {
  nombreFiestaCompartida: string = '';

  constructor() { }

  establecerNombreFiesta(frase: string) {
    this.nombreFiestaCompartida = frase;
  }

  obtenerNombreFiesta() {
    return this.nombreFiestaCompartida;
  }
}
