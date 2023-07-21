import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class PeticionEliminarArchivos {
  constructor(private http: HttpClient) { }

  urlBase = environment.APIUrlBase;
  urlEliminar = environment.APIUrlEliminarArchivos
  urlComplit = this.urlBase + this.urlEliminar

  getEliminarDatos(): Observable<string> {

    return this.http.get<any>(this.urlComplit).pipe(
      map((response) => {
        if (response && response.Exito) {
          return response.Exito;
        } else {
            throw new Error("Error en la solicitud")
        }
      }), catchError((error) => {
        console.error('No se pudo eliminar los archivos:', error);
        throw new Error('No se pudo eliminar los archivos');
      })
    );
  }

}
