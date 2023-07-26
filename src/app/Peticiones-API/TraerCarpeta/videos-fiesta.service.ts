import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class MostrarVideosService {
  currentUser: string | null = "";

  constructor(private http: HttpClient) {
    this.currentUser = localStorage.getItem('currentUser');
  }

  respuesta: any;
  urlBase = environment.APIUrlBase;
  urlTraer = environment.APIUrlTraerVideo;
  urlFinal = this.urlBase + this.urlTraer;

  getPeticionVideos(nombreFiesta: string) {
    const url = this.urlFinal + nombreFiesta + "&" + environment.APIusuarios + this.currentUser;;
    return this.http.get(url);
  }

  getInfo(nombreFiesta: string) {
    return this.getPeticionVideos(nombreFiesta).pipe(
      map((response) => {
        this.respuesta = response;

      }), catchError((error) => {
        console.error(error);
        throw new Error('La carpeta no existe o esta vacia');
      })
    );
  }
}

