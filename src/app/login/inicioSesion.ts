import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InicioSesionUser {
  isExist: boolean = false;

  constructor() { }

  establecerUserCompartida(user: boolean) {
    this.isExist = user;
    console.log("Establecer: "+this.isExist)

  }

  obtenerUserCompartida() {
    console.log("obtener1: "+this.isExist)

    return this.isExist;
  }
}
