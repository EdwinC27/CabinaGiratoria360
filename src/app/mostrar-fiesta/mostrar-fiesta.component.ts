import { Component, ElementRef, OnInit } from '@angular/core';
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

  constructor(private router: ActivatedRoute, private mostrarFiestaService: MostrarFiestaService, private elementRef: ElementRef) { }

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
    const videosDiv = this.elementRef.nativeElement.querySelector('#videos');
    let html = '';
    for (const key in this.url) {
      if (this.url.hasOwnProperty(key)) {
        let videoUrl = this.url[key];
        const safe = videoUrl.replace('?dl=0', '') + "?raw=1"

        html += `<video width="320" height="240" controls>
          <source src="${safe}" type="video/mp4">
        </video>`;
      }
    }
    videosDiv.innerHTML = html;
  }
}
