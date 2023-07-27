import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class MostrarCarpetasService {
  constructor(private http: HttpClient) { }

  respuesta: any;
  urlBase = environment.APIUrlBase;
  urlTraer = environment.APIUrlTraerCarpetas;
  urlFinal = "" ;

  getMostrarCarpetas(currentUser: any): Observable<string[]> {
    this.urlFinal = this.urlBase + this.urlTraer + "?" + environment.APIusuarios + currentUser;

    console.log(this.urlFinal)

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

