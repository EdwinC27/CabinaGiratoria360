import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class MostrarVideosService {
  constructor(private http: HttpClient) { }

  respuesta: any;
  urlBase = environment.APIUrlBase;
  urlTraer = environment.APIUrlTraerVideo;
  urlFinal = this.urlBase + this.urlTraer;

  getPeticionVideos(nombreFiesta: string, currentUser: any) {
    const url = this.urlFinal + nombreFiesta + "&" + environment.APIusuarios + currentUser;;
    return this.http.get(url);
  }

  getInfo(nombreFiesta: string, currentUser: any) {
    return this.getPeticionVideos(nombreFiesta, currentUser).pipe(
      map((response) => {
        this.respuesta = response;

      }), catchError((error) => {
        console.error('La carpeta no existe:', error);
        alert("Error")
        throw new Error('La carpeta no existe');
      })
    );
  }
}

