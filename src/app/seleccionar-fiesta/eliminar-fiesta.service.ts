import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EliminarFiestaService {
  constructor(private http: HttpClient) { }

  respuesta: any;
  urlBase = "http://localhost:8080/api/archivos?accion=Delete&fiesta=";


  getEliminarDatos(fiesta: string): Observable<any> {
    const url = this.urlBase + fiesta;
    return this.http.get(url);
  }
  
  getInfo(fiesta: string) {
    return this.getEliminarDatos(fiesta);
  }
}
