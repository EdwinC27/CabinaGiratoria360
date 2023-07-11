import { Component, OnInit } from '@angular/core';
import { MostrarVideosService } from '../Peticiones-API/TraerCarpeta/videos-fiesta.service';

@Component({
  selector: 'app-acceder-carpeta',
  templateUrl: './acceder-carpeta.component.html',
  styleUrls: ['./acceder-carpeta.component.css']
})
export class AccederCarpetaComponent implements OnInit{
  videoUrls: any;
  url: any;
  interval: any;
  responseData: any;

  constructor (private mostrarVideosService: MostrarVideosService){}

  ngOnInit(): void {
    this.peticion()

    this.interval = setInterval(() => {
      this.peticion();
    }, 90_000); // tiempo en segundos (1 minuto y 30 segundos)
  }

  peticion(): void {
    this.mostrarVideosService.getInfo("edwuin").subscribe(
      () => {
        this.url = this.mostrarVideosService.respuesta;
        this.videoUrls = Object.values(this.url);
       },
      (error) => {
        console.log(error);
      }
    )
  }
}
