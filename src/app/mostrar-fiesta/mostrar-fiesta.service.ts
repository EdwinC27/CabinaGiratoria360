import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MostrarFiestaService {
  constructor(private http: HttpClient) { }

  respuesta: any;
  urlBase = "http://localhost:8080/api/archivos?accion=GetUrl&fiesta=";


  getPeticionVideos(fiesta: string) {
    const url = this.urlBase + fiesta;
    return this.http.get(url);
  }

  getInfo(fiesta: string) {
    return this.getPeticionVideos(fiesta).pipe(
      map((response: any) => {
        this.respuesta = response;
      })
    );
  }
}
