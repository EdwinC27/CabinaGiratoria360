import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class URLTokenService {
  constructor(private http: HttpClient) { }
  urlBase = "http://localhost:8080/dropbox/auth";

  getPeticionUrlToken() {
    return this.http.get(this.urlBase);
  }
}
