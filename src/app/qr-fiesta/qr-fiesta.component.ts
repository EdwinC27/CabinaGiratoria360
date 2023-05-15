import { Component, OnInit } from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { FileService } from '../file.service';
import { FraseService } from '../frase.service';
import { LastFileAdd } from './lastFile-fiesta.service';

@Component({
  selector: 'app-qr-fiesta',
  templateUrl: './qr-fiesta.component.html',
  styleUrls: ['./qr-fiesta.component.css']
})
export class QrFiestaComponent implements OnInit {
  id:any;
  imgQR: any;
  frase: string = " ";
  safeSrc: SafeResourceUrl | undefined;
  videoUrls: string[] | undefined;
  safeVideoUrl: SafeResourceUrl | undefined;
  logo: any;
  url: any;
  interval: any;
  imagenUrls: string[] | undefined;
  videoActual: number = 0;

  constructor (private router: ActivatedRoute, private fraseService: FraseService,private lastFileAdd: LastFileAdd, private fileService: FileService){}

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");
    this.frase = this.fraseService.obtenerFraseCompartida();
    this.imgQR = "../../assets/QRs/fiesta" + this.id + ".png";
    this.logo = this.fileService.obtenerFileCompartida()

    this.miMetodo()

    this.interval = setInterval(() => {
      this.miMetodo();
    }, 90_000); // tiempo en segundos (1 minuto y 30 segundos)
  }

  miMetodo(): void {
    this.lastFileAdd.getInfo(this.id).subscribe(
      () => {
        this.url = this.lastFileAdd.respuesta;
        this.mostrarVideos();
      },
      (error) => {
        console.log(error);
      }
    )
  }


  mostrarVideos(): void {
    this.videoUrls = Object.values(this.url);
    this.videoUrls = this.videoUrls.map(videoUrl => videoUrl.replace('?dl=0', '') + '?raw=1');
    this.imagenUrls = this.videoUrls;
  }

  setActiveIndex(indice: number): void {
    this.videoActual = indice;
  }
}
