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

  credencialesErronias: boolean = false;

  user: string = "";
  contrasena: string = "";

  isAuthenticated: boolean = false;

  ngOnInit(): void {
    localStorage.clear()
  }

  onSubmit(username: string, password: string) {
    this.authService.login(username, password).subscribe((isAuthenticated: boolean) => {
      if (isAuthenticated) {
        localStorage.setItem('currentUser', username);
        this.router.navigate(['/menu']);
      } else {
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
