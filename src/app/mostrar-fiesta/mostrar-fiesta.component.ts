import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MostrarFiestaService } from './mostrar-fiesta.service';

@Component({
  selector: 'app-mostrar-fiesta',
  templateUrl: './mostrar-fiesta.component.html',
  styleUrls: ['./mostrar-fiesta.component.css']
})
export class MostrarFiestaComponent implements OnInit{
  url: any;
  id:any;
  videoUrls: string[] | undefined;

  constructor(private router: ActivatedRoute, private mostrarFiestaService: MostrarFiestaService) { }

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");

    this.mostrarFiestaService.getInfo(this.id).subscribe(
      () => {
        this.url = this.mostrarFiestaService.respuesta;
        this.mostrarVideos();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  mostrarVideos(): void {
    this.videoUrls = Object.values(this.url);
    let urls;

    for (let i = 0; i < this.videoUrls.length; i++) {
      const jsonString = JSON.stringify(this.videoUrls[i]); 
      urls = jsonString.replace(/[\[\]\\"]/g, "").split(",");
      for (let j = 0; j < urls.length; j++) {
        urls[j] = urls[j].replace('?dl=0', '?raw=1');
      }
      console.log(urls);
    }
    this.videoUrls = urls;
  }
}
