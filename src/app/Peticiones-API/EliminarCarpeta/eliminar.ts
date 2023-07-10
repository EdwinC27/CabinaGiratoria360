import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PeticionEliminarCarpeta {
  constructor(private http: HttpClient) { }
  urlBase = "http://18.118.247.152:8080/api/carpeta/eliminar?carpeta=";
  url = "";

  getEliminarFiesta(nombreFiesta: string): Observable<string> {
    this.url = this.urlBase + nombreFiesta;

    return this.http.get<any>(this.url).pipe(
      map((response) => {
        if (response && response.Exito) {
          return response.Exito;
        } else {
            throw new Error("Erro en la solicitud")
        }
      }), catchError((error) => {
        console.error('La carpeta no existe:', error);
        throw new Error('La carpeta no existe');
      })
    );
  }

}
