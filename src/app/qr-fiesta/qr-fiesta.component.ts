import { Component, OnInit } from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { FileService } from '../file.service';
import { FraseService } from '../frase.service';
import { MostrarVideosService } from '../videos-fiesta.service';

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
  vueltas: any = [1, 2, 3, 4, 5, 6, 7]
  constructor (private router: ActivatedRoute, private fraseService: FraseService,private mostrarVideosService: MostrarVideosService, private fileService: FileService, private router2: Router){}

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");
    this.frase = this.fraseService.obtenerFraseCompartida();
    this.imgQR = "../../assets/QRs/fiesta" + this.id + ".png";
    this.logo = this.fileService.obtenerFileCompartida()

    if(this.logo == "") {
      this.logo = "../../assets/logo/logoEmpresa.png"
    }
    
    this.peticion()

    this.interval = setInterval(() => {
      this.peticion();
    }, 90_000); // tiempo en segundos (1 minuto y 30 segundos)
  }

  peticion(): void {
    this.mostrarVideosService.getInfo(this.id).subscribe(
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
    this.videoUrls = Object.values(this.url);
    let urls;

    if(this.videoUrls.includes("Token vacio")) {
      alert("Error te falta obtener un token de acceso");
      console.log("Error te falta obtener un token de acceso:  "+ this.videoUrls);
    } else if (this.videoUrls.includes("Exception in 2\/files\/list_folder: {\".tag\":\"path\",\"path\":\"not_found\"}")) {
      alert("Carpeta no encontrada");
      console.log("Carpeta no encontrada"+ this.videoUrls);
    } else {
      for (let i = 0; i < this.videoUrls.length; i++) {
        const jsonString = JSON.stringify(this.videoUrls[i]);
        urls = jsonString.replace(/[\[\]\\"]/g, "").split(",");
        for (let j = 0; j < urls.length; j++) {
          urls[j] = urls[j].replace('?dl=0', '?raw=1');
        }
        console.log(urls);
      }
      this.imagenUrls = urls;
    }
  }

  setActiveIndex(indice: number): void {
    this.videoActual = indice;
  }

  previousVideo(): void {
    if(this.videoActual - 1  < 0){
      this.videoActual = 4;
    } else {
      this.videoActual--;
    }
    console.log("Previos: " +this.videoActual)
  }

  nextVideo(): void {
    if(this.videoActual + 1  > 4){
      this.videoActual = 0;
    } else {
      this.videoActual++;
    }
    console.log("Next: "+this.videoActual)
  }
}
