import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environmet';

@Injectable({
  providedIn: 'root'
})
export class PeticionDescargarCarpeta {
  constructor(private http: HttpClient) { }

  urlBase = environment.APIUrlBase;
  urlDescargar = environment.APIUrlDescargarCarpeta
  urlComplit = this.urlBase + this.urlDescargar

  onDownloadFolder(folderName: string, usuario: any) {
    const headers = new HttpHeaders({ responseType: 'arraybuffer' });
    this.http
      .get(`${this.urlComplit}?carpeta=${folderName}&usuario=${usuario}`, {
        headers,
        responseType: 'arraybuffer',
        observe: 'response',
      })
      .subscribe((response: HttpResponse<ArrayBuffer>) => {
        if (response.body !== null) {
          this.downloadFile(response.body, folderName + '.zip');
        } else {
          console.error('La respuesta del servidor no contiene datos para descargar.');
        }
    });
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
