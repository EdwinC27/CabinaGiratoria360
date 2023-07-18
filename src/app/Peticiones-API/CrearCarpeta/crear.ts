import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environmet';
import { FraseService } from 'src/app/Frase/frase.service';

@Injectable({
  providedIn: 'root'
})
export class PeticionCrearCarpeta {
  constructor(private http: HttpClient, private fraseService: FraseService) { }

  urlBase = environment.APIUrlBase;
  urlCrear = environment.APIUrlCrearCarpeta;
  urlComplit = this.urlBase + this.urlCrear;
  url = "";
  frase = this.fraseService.obtenerFraseCompartida();

  getCrearFiesta(nombreFiesta: string): Observable<string> {
    if(this.frase == "") this.frase = "www.rockolasguadalajara.com"

    this.url = this.urlComplit + nombreFiesta + "&archivo=" + this.frase;

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
