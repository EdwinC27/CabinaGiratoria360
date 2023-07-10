import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  fileCompartida: string = '';

  constructor() { }

  establecerFileCompartida(file: string) {
    this.fileCompartida = file;
  }

  obtenerFileCompartida() {
    return this.fileCompartida;
  }
}