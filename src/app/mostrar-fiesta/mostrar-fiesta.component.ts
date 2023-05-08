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

  constructor(private router: ActivatedRoute, private mostrarFiestaService: MostrarFiestaService) { }

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");

    this.mostrarFiestaService.getInfo(this.id).subscribe(
      () => {
        this.url = this.mostrarFiestaService.respuesta;
        console.log(this.url)
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
