import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class MostrarCarpetasService {
  currentUser: string | null = "";

  constructor(private http: HttpClient) {
    this.currentUser = localStorage.getItem('currentUser');
  }

  respuesta: any;
  urlBase = environment.APIUrlBase;
  urlTraer = environment.APIUrlTraerCarpetas;
  urlFinal = "" ;

  getMostrarCarpetas(): Observable<string[]> {
    this.urlFinal = this.urlBase + this.urlTraer + "?" + environment.APIusuarios + this.currentUser;

    return this.http.get<any>(this.urlFinal).pipe(
      map((response) => {
        const carpetas = Object.keys(response);
        return carpetas;
      }),
      catchError((error) => {
        console.error(error);
        throw new Error("Error en la solicitud");
      })
    );
  }

}

