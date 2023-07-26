import { Injectable } from '@angular/core';
import { TraerUsuarios } from 'src/app/Peticiones-API/usuarios/traerUsuarios';
import { InicioSesionUser } from '../inicioSesion';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated = false;

  constructor(private traerUsuarios: TraerUsuarios, private inicioSesionUser: InicioSesionUser) { }

  login(username: string, password: string): Observable<boolean> {
    return this.traerUsuarios.getUser(username, password).pipe(
      tap((message) => {
        this.isAuthenticated = message;
        this.inicioSesionUser.establecerUserCompartida(message);
      }),
      catchError((error) => {
        console.error(error);
        return of(false); // Devuelve un Observable que emite un valor booleano "false" en caso de error.
      })
    );
  }

  logout() {
    // Implementa aquí la lógica para cerrar sesión y establecer isAuthenticated en false.
    this.isAuthenticated = false;
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }
}
