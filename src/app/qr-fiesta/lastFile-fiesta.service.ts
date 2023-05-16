import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LastFileAdd {
  constructor(private http: HttpClient) { }

  respuesta: any;
  urlBase = "http://localhost:8080/api/archivos?accion=GetLastMp4&fiesta=";


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
