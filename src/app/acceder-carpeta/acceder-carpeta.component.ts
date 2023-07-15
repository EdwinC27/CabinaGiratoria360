import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FileService } from '../FileLogoFiesta/file.service';
import { FraseService } from '../Frase/frase.service';
import { environment } from '../../environments/environmet';
import { NombreFiestaService } from '../NombreFiesta/Nombre.Fiesta.Service'
import { MostrarVideosService } from '../Peticiones-API/TraerCarpeta/videos-fiesta.service';

import * as QRCode from 'qrcode';

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

  constructor (private nombreFiesta: NombreFiestaService, private fraseService: FraseService,  private fileService: FileService, private mostrarVideosService: MostrarVideosService, private cdr: ChangeDetectorRef){}

  showNextVideo() {
    this.currentVideoIndex = (this.currentVideoIndex + 1) % this.videoUrls.length;
  }

  showPreviousVideo() {
    this.currentVideoIndex = (this.currentVideoIndex - 1 + this.videoUrls.length) % this.videoUrls.length;
  }

  ngOnInit(): void {
    this.generateQRCode();
    this.frase = this.fraseService.obtenerFraseCompartida();
    this.logo = this.fileService.obtenerFileCompartida()

    if(this.logo == "") this.logo = "../../favicon.ico"

    if(this.frase == "") this.frase = "www.rockolasguadalajara.com"

    this.peticion()

    this.interval = setInterval(() => {
      this.peticion();
    }, 90_000); // tiempo en segundos (1 minuto y 30 segundos)
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
    console.log(this.videoUrls);
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
}
