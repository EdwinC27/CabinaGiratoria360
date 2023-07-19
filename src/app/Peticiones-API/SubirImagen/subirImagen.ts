import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class PeticionAddImagen {
  constructor(private http: HttpClient) { }

  urlBase = environment.APIUrlBase;
  urlimagne = environment.APIUrlAgregarImagen;
  urlComplit = this.urlBase + this.urlimagne;
  url = "";

  addImagen(formData: FormData, nombreFiesta: string) {
    this.url =  this.urlComplit + nombreFiesta;
    console.log(this.url)
    this.http.post(this.url, formData).subscribe(
        (response) => {
            console.log('Imagen subida con éxito');
          },
          (error) => {
            console.error('Error al subir la imagen:', error);
          }
        );
  }
}
