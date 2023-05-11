import { Component, OnInit } from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
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

  url: any;

  constructor (private router: ActivatedRoute, private fraseService: FraseService,private lastFileAdd: LastFileAdd){}

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");
    this.frase = this.fraseService.obtenerFraseCompartida();
    this.imgQR = "../../assets/QRs/fiesta" + this.id + ".png";

    this.lastFileAdd.getInfo(this.id).subscribe(
      () => {
        this.url = this.lastFileAdd.respuesta;
        this.mostrarVideos();
      },
      (error) => {
        console.log(error);
      }
    );
  }


  mostrarVideos(): void {
    this.videoUrl = Object.values(this.url);

    if(this.videoUrl != "No se encontraron archivos de vídeo en la carpeta especificada.") {
      this.videoUrl = this.videoUrl.map((videoUrls: string) => videoUrls.replace('?dl=0', '') + '?raw=1');
    }
    else {
      alert(this.videoUrl)
    }
  }
}
