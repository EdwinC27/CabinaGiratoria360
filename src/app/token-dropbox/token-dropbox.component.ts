import { Component, OnInit } from '@angular/core';
import { URLTokenService } from './token-dropbox.service';

@Component({
  selector: 'app-token-dropbox',
  templateUrl: './token-dropbox.component.html',
  styleUrls: ['./token-dropbox.component.css']
})
export class TokenDropboxComponent implements OnInit {
  url: any;
  constructor (private UrlTokenService: URLTokenService){}

  ngOnInit(): void {
    this.UrlTokenService.getPeticionUrlToken().subscribe(
      (response: any) => {
        this.url = response.URL;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }  
  
}
