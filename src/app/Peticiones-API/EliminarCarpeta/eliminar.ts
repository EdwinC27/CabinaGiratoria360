import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class PeticionEliminarCarpeta {
  currentUser: string | null = "";

  constructor(private http: HttpClient) {
    this.currentUser = sessionStorage.getItem('currentUser');
  }

  urlBase = environment.APIUrlBase;
  urlEliminar = environment.APIUrlEliminarCarpeta
  urlComplit = this.urlBase + this.urlEliminar
  url = "";

  getEliminarFiesta(nombreFiesta: string, currentUser: any): Observable<string> {
    this.url = this.urlComplit + nombreFiesta;
    this.url = this.urlComplit + nombreFiesta + "&" + environment.APIusuarios + currentUser;

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
