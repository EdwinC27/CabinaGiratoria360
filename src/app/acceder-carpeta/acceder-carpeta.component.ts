import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
  evento: any;

  constructor (private mostrarVideosService: MostrarVideosService, private router: ActivatedRoute){}

  ngOnInit(): void {
    this.evento = this.router.snapshot.paramMap.get("evento");

    this.peticion(this.evento)

    this.interval = setInterval(() => {
      this.peticion(this.evento);
    }, 90_000); // tiempo en segundos (1 minuto y 30 segundos)
  }

  peticion(evento: any): void {
    this.mostrarVideosService.getInfo(evento).subscribe(
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
