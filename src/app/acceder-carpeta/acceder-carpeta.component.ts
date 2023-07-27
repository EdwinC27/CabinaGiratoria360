import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MostrarVideosService } from '../Peticiones-API/TraerCarpeta/videos-fiesta.service';

@Component({
  selector: 'app-acceder-carpeta',
  templateUrl: './acceder-carpeta.component.html',
  styleUrls: ['./acceder-carpeta.component.css']
})
export class AccederCarpetaComponent implements OnInit {
  videoUrls: any;
  url: any;
  interval: any;
  responseData: any;
  evento: any;

  currentPage = 1;
  videosPerPage = 5;

  usuario: any;

  constructor(private mostrarVideosService: MostrarVideosService, private router: ActivatedRoute) {}

  ngOnInit(): void {
    this.evento = this.router.snapshot.paramMap.get("evento");
    this.usuario = this.router.snapshot.paramMap.get("usuario");

    this.updateVideosAndPagination();
    this.interval = setInterval(() => this.updateVideosAndPagination(), 180000); // 180,000 ms = 3 minuto
  }

  updateVideosAndPagination(): void {
    this.mostrarVideosService.getInfo(this.evento, this.usuario).subscribe(
      () => {
        this.url = this.mostrarVideosService.respuesta;
        this.videoUrls = Object.values(this.url.videos).map((video: any) => Object.values(video)[0]);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  range(start: number, end: number): number[] {
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }

  changePage(page: number): void {
    this.currentPage = page;
  }

  separateVideos(currentPage: number, videosPerPage: number) {
    const startIndex = (currentPage - 1) * videosPerPage;
    return this.videoUrls.slice(startIndex, startIndex + videosPerPage);
  }

  getTotalPages(): number {
    if (!this.videoUrls) {
      return 0;
    }
    return Math.ceil(this.videoUrls.length / this.videosPerPage);
  }
}
