import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    localStorage.clear()
  }

  onSubmit(username: string, password: string) {
    // Aquí puedes llamar al método de autenticación del servicio con las credenciales ingresadas.
    const isAuthenticated = this.authService.login(username, password);

    if (isAuthenticated) {
      localStorage.setItem('currentUser', username);

      this.router.navigate(['/menu']);
    } else {
      // Si la autenticación falla, puedes mostrar un mensaje de error o realizar alguna otra acción.
      alert("Fallo")
    }
  }
}
