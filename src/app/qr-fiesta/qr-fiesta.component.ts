import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-qr-fiesta',
  templateUrl: './qr-fiesta.component.html',
  styleUrls: ['./qr-fiesta.component.css']
})
export class QrFiestaComponent implements OnInit {
  id:any;
  imgQR: any;
  frase: string = "Que esta fiesta sea una oportunidad para renovar energías, fortalecer lazos y crear nuevos recuerdos que perduren para siempre en tu memoria. ¡Celebra con ganas, ríe sin parar, baila hasta el amanecer y disfruta de cada instante! Que esta fiesta te traiga mucha dicha, amor y paz en tu vida. ¡Feliz Fiesta!";
  safeSrc: SafeResourceUrl | undefined;


  constructor (private router: ActivatedRoute, private sanitizer: DomSanitizer){}

  ngOnInit(): void {
    this.id = this.router.snapshot.paramMap.get("id");
    this.imgQR = "../../assets/QRs/fiesta" + this.id + ".png";
    this.safeSrc =  this.sanitizer.bypassSecurityTrustResourceUrl("https://www.youtube.com/embed/4HW-JFuKfH4");
  }
}
