import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../../environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class PeticionCrearCarpeta {
  constructor(private http: HttpClient) { }

  urlBase = environment.APIUrlBase;
  urlCrear = environment.APIUrlCrearCarpeta;
  urlComplit = this.urlBase + this.urlCrear;
  url = "";

  getCrearFiesta(nombreFiesta: string): Observable<string> {
    this.url = this.urlComplit + nombreFiesta;

    return this.http.get<any>(this.url).pipe(
      map((response) => {
        if (response && response.Exito) {
          return response.Exito;
        } else {
            throw new Error("Erro en la solicitud")
        }
      }), catchError((error) => {
        console.error('La carpeta ya existe:', error);
        throw new Error('La carpeta ya existe');
      })
    );
  }

}
