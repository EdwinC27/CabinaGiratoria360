import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';
import { InicioSesionUser } from './inicioSesion';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private inicioSesionUser: InicioSesionUser) {}

  correo: string = "mailto:edwin.a.castillo.27@gmail.com";
  linkedin: string = "https://www.linkedin.com/in/edwin-alejandro-castillo-arroyo-276226243/";
  rutaLogo: string = "../../assets/img/logo.png";

  credencialesErronias: boolean = false;

  user: string = "";
  contrasena: string = "";

  isAuthenticated: boolean = false;

  ngOnInit(): void {
    sessionStorage.clear()
  }

  onSubmit(username: string, password: string) {
    this.authService.login(username, password).subscribe((isAuthenticated: boolean) => {
      if (isAuthenticated && username) {
        sessionStorage.setItem('currentUser', username);

        this.router.navigate(['/menu']);

      } else {
        sessionStorage.removeItem('currentUser');

        this.credencialesErronias = true;
        this.user = "";
        this.contrasena = "";

        setTimeout(() => {
          this.credencialesErronias = false;

        }, 2000);
      }
    }, (error) => {
      console.error(error);
    });
  }

  // Función que determina si el botón debe estar deshabilitado
  isButtonDisabled(): boolean {
    if (this.user === "" || this.contrasena === "") {
      return true;
    }

    return false;
  }
}
