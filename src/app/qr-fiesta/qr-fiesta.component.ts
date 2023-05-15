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
  videoUrl: any;
  safeVideoUrl: SafeResourceUrl | undefined;
  logo: any;
  url: any;
  interval: any;
  imagenUrls: string[] = ["../../assets/videos/1.mp4", "../../assets/videos/1.mp4", "../../assets/videos/2.mp4", "../../assets/videos/edwibn.mp4"];

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
    this.videoUrl = null;
  }


  mostrarVideos(): void {
    this.videoUrl = Object.values(this.url);

    if(this.videoUrl != "No se encontraron archivos de vídeo en la carpeta especificada.") {
      this.videoUrl = this.videoUrl.map((videoUrls: string) => videoUrls.replace('?dl=0', '') + '?raw=1');
      console.log(this.videoUrl)
    }
    else {
      alert(this.videoUrl)
    }
  }
}
