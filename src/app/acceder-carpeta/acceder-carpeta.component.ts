import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FileService } from '../FileLogoFiesta/file.service';
import { environment } from '../../environments/environmet';
import { NombreFiestaService } from '../NombreFiesta/Nombre.Fiesta.Service'
import { MostrarVideosService } from '../Peticiones-API/TraerCarpeta/videos-fiesta.service';

import * as QRCode from 'qrcode';
import { Router } from '@angular/router';

@Component({
  selector: 'app-acceder-carpeta',
  templateUrl: './acceder-carpeta.component.html',
  styleUrls: ['./acceder-carpeta.component.css']
})
export class AccederCarpetaComponent implements OnInit{
  frase: string = " ";
  logo: any;
  qrCodeImage: string | undefined;
  textnombreFiesta = this.nombreFiesta.obtenerNombreFiesta();

  videoUrls: any;
  url: any;
  interval: any;
  currentVideoIndex = 0;
  responseData: any;
  mensajeEliminacion: string = "";

  videoActual: number = 0;

  currentUser: string | null;

  constructor (private nombreFiesta: NombreFiestaService, private fileService: FileService, private mostrarVideosService: MostrarVideosService, private cdr: ChangeDetectorRef, private router: Router) {
    this.currentUser = localStorage.getItem('currentUser');
  }

  showNextVideo() {
    this.currentVideoIndex = (this.currentVideoIndex + 1) % this.videoUrls.length;
    this.videoActual = this.currentVideoIndex;
  }

  showPreviousVideo() {
    this.currentVideoIndex = (this.currentVideoIndex - 1 + this.videoUrls.length) % this.videoUrls.length;
    this.videoActual = this.currentVideoIndex;
  }

  ngOnInit(): void {
    if (this.currentUser == null) {
      this.router.navigate(['/login']);
    }
    this.generateQRCode();
    this.logo = this.fileService.obtenerFileCompartida()

    this.peticion()

    this.interval = setInterval(() => {
      this.peticion();
    }, 120_000); // tiempo en segundos (1 minuto y 30 segundos)
  }

  peticion(): void {
    this.mostrarVideosService.getInfo(this.textnombreFiesta).subscribe(
      () => {
        this.url = this.mostrarVideosService.respuesta;
        this.mostrarVideos();
       },
      (error) => {
        console.log(error);
      }
    )
  }

  mostrarVideos(): void {
    this.videoUrls = Object.values(this.url.videos).map((video: any) => Object.values(video)[0]);
    this.frase = this.url.txt;
    this.logo = this.url.logo;

    if(this.logo == "" || this.logo === undefined) this.logo = "../../favicon.ico"

    if (this.videoUrls.length > 5) {
      this.videoUrls = this.videoUrls.slice(0, 5);
    }
  }

  generateQRCode(): void {
    const qrCodeData = environment.URLPaginaPublica + this.textnombreFiesta

    QRCode.toDataURL(qrCodeData, (error, url) => {
      if (error) {
        console.error(error);
        return;
      }
      this.qrCodeImage = url;
    });
  }

  setActiveIndex(indice: number): void {
    this.videoActual = indice;
  }
}
