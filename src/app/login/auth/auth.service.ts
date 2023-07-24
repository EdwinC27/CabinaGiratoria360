import { Injectable } from '@angular/core';
import { TraerUsuarios } from 'src/app/Peticiones-API/usuarios/traerUsuarios';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated = false;

  constructor(private traerUsuarios: TraerUsuarios) { }

  login(username: string, password: string): boolean {
    const respuestaUser = this.traerUsuarios.getUser(username, password).subscribe((message) => {
        console.log(message)
    }, (error) => {
      console.error(error)
    });

    
    if (respuestaUser) {
      this.isAuthenticated = true;
      return true;
    } else {
      this.isAuthenticated = false;
      return false;
    }
  }

  logout() {
    // Implementa aquí la lógica para cerrar sesión y establecer isAuthenticated en false.
    this.isAuthenticated = false;
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }
}
