import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class TraerUsuarios {
  constructor(private http: HttpClient) { }

  urlBase = environment.APIUrlBase;
  APIUrlTraerUsuiarios = environment.APIUrlTraerUsuiarios;
  urlComplit = this.urlBase + this.APIUrlTraerUsuiarios;
  url = "";

  getUser(user: string, password: string) {
    this.url =  this.urlComplit + user + "&password=" + password;

    return this.http.get<any>(this.url).pipe(
      map((response) => {
          return response;

      }), catchError((error) => {
        console.error('No se pudo eliminar los archivos:', error);
        throw new Error('No se pudo eliminar los archivos');
      })
    );
  }
}
