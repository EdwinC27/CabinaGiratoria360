import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FileService } from '../file.service';
import { FraseService } from '../frase.service';
import { EliminarFiestaService } from './eliminar-fiesta.service';
import { URLTokenService } from './token-dropbox.service';

@Component({
  selector: 'app-seleccionar-fiesta',
  templateUrl: './seleccionar-fiesta.component.html',
  styleUrls: ['./seleccionar-fiesta.component.css']
})
export class SeleccionarFiestaComponent implements OnInit {
  inputText: string = '';
  inputMensage: string = '';
  id: any;
  resultado: any;

  constructor(private router: Router, private fraseService: FraseService, private eliminarFiestaService: EliminarFiestaService, private fileService: FileService, private UrlTokenService: URLTokenService) { }

  buscarQR() {
    if (parseInt(this.inputText) >= 1) {
      this.id = parseInt(this.inputText);

      // establecer la frase compartida
      this.fraseService.establecerFraseCompartida(this.inputMensage);

      this.router.navigate(['/qr', this.id]);
    } else {
      alert("Número de fiesta inválido");
    }
  }

  confirmarEliminacion() {
    if (confirm("¿Está seguro de que desea eliminar los archivos de la fiesta " + this.inputText + "?")) {
      this.eliminarArchivos();
    } else {
      alert("Operación cancelada")
    }
  }

  eliminarArchivos() {
    if (parseInt(this.inputText) <= 4 && parseInt(this.inputText) >= 1) {
      this.eliminarFiestaService.getInfo(this.inputText).subscribe(
        data => {
          let miCadenaDeTexto = JSON.stringify(data);

          let partes = miCadenaDeTexto.split(":");

          partes[1] = partes[1].replace('"}', '');
          partes[1] = partes[1].replace('"', '');


          alert(partes[1]);
        },
        error => {
          console.error(error);
        }
      )
    } else {
      alert("Número de fiesta inválido");
    }
  }

  async seleccionarArchivo(event: any) {
    const imagen = event.target.files[0];
    const resultado = await this.convertirImagenABase64(imagen);
    this.resultado = resultado.base64
  }

  subirArchivo() {
    this.fileService.establecerFileCompartida(this.resultado);
    alert("Archivo subido exitosamente")
  }

  // función que convierte una imagen a base64
  public async convertirImagenABase64(imagen: File): Promise<{base64: string | null}> {
    try {
      const reader = new FileReader();
      reader.readAsDataURL(imagen);
      const result = await new Promise<string | null>((resolve, reject) => {
        reader.onload = () => resolve(reader.result as string);
        reader.onerror = error => resolve(null);
      });
      return { base64: result };
    } catch (error) {
      console.error('Error al convertir imagen a base64:', error);
      return { base64: null };
    }
  }

  // Obtener token
  url: any;
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
