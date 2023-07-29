import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environmet';
import { Observable, throwError } from 'rxjs'; // Asegúrate de importar Observable
import { map, catchError } from 'rxjs/operators'; // Asegúrate de importar Observable

@Injectable({
  providedIn: 'root'
})
export class PeticionDescargarCarpeta {
  constructor(private http: HttpClient) { }

  urlBase = environment.APIUrlBase;
  urlDescargar = environment.APIUrlDescargarCarpeta
  urlComplit = this.urlBase + this.urlDescargar

  onDownloadFolder(folderName: string, usuario: any): Observable<ArrayBuffer | null> {
    const headers = new HttpHeaders({ responseType: 'arraybuffer' });
    return this.http.get(`${this.urlComplit}?carpeta=${folderName}&usuario=${usuario}`, {
      headers,
      responseType: 'arraybuffer',
      observe: 'response',
    }).pipe(
      map((response: HttpResponse<ArrayBuffer>) => {
        if (response.body !== null) {
          this.downloadFile(response.body, folderName + '.zip');
          return response.body;
        } else {
          console.error('La respuesta del servidor no contiene datos para descargar.');
          return null;
        }
      }),
      catchError((error: any) => {
        console.error('Error al descargar el archivo:', error);
        return throwError('Error al descargar el archivo'); 
      })
    );
  }

  private downloadFile(data: ArrayBuffer, filename: string) {
    const blob = new Blob([data], { type: 'application/zip' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
