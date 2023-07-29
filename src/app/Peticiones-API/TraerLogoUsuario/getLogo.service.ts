import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs';
import { environment } from 'src/environments/environmet';
import { AES, enc } from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class TraerLogo {
  constructor(private http: HttpClient) { }

  respuesta: any;
  urlBase = environment.APIUrlBase;
  urlTraer = environment.APIUrlLogo;
  urlFinal = this.urlBase + this.urlTraer;

  getLogoUser(currentUser: any) {

    const url = this.urlFinal + currentUser;
    return this.http.get(url);
  }

  getInfo(currentUser: any) {
    return this.getLogoUser(currentUser).pipe(
      map((response) => {
        this.respuesta = response;

      }), catchError((error) => {
        console.error(error);
        throw new Error('La carpeta no existe o esta vacia');
      })
    );
  }

}

