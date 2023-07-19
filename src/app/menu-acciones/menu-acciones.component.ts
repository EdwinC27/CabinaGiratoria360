import { Component, ChangeDetectorRef, Renderer2, OnInit } from '@angular/core';
import { FileService } from '../FileLogoFiesta/file.service';
import { FraseService } from '../Frase/frase.service';
import { Router } from '@angular/router';
import { NombreFiestaService } from '../NombreFiesta/Nombre.Fiesta.Service';
import { PeticionEliminarArchivos } from '../Peticiones-API/EliminarArchivos/eliminarArchivos';
import { MostrarCarpetasService } from '../Peticiones-API/TraerNombresDeCarpetas/videos-fiesta.service';
import { HttpClient } from '@angular/common/http';
import { PeticionAddImagen } from '../Peticiones-API/SubirImagen/subirImagen';

@Component({
  selector: 'app-menu-acciones',
  templateUrl: './menu-acciones.component.html',
  styleUrls: ['./menu-acciones.component.css']
})
export class MenuAccionesComponent implements OnInit {
  selectedOption: string = 'Seleccionar';
  selectedOptionCarpeta: string = 'Seleccionar';
  selectedOptionDefault: string = 'Seleccionar';

  inputText: string = '';
  inputMensage: string = '';
  resultado: any;

  responseData: any;
  responseCarpetas: any;

  carpetas: any;

  logoRuta = "../../assets/img/logo.jpeg";

  isPopupVisible: boolean = false;

  public showOverlay = true;

  selectedFile: File | null = null;

  constructor(private fraseService: FraseService, private fileService: FileService, private nombreFiesta: NombreFiestaService, private router: Router, private peticionEliminarArchivos: PeticionEliminarArchivos, private mostrarCarpetasService: MostrarCarpetasService, private cdr: ChangeDetectorRef, private renderer: Renderer2, private http: HttpClient, private peticionAddImagen: PeticionAddImagen) { }

  async seleccionarArchivo(event: any) {
    this.selectedFile = event.target.files[0];

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

  ngOnInit(): void {
    this.mostrarCarpetasService.getMostrarCarpetas().subscribe((carpeta) => {
      this.carpetas = carpeta;
    }, (error) => {
      this.showPopup(error);
    });

    setTimeout(() => {
      this.showOverlay = false;
    }, 2000);
  }

  accion(selectedOption: string) {
    if (selectedOption === "Acceder a evento") {
      this.nombreFiesta.establecerNombreFiesta(this.selectedOptionCarpeta)
      this.router.navigate(['/acceder']);
    }

    if (selectedOption === "Crear evento") {
      if (this.selectedFile) {
        const formData = new FormData();
        formData.append('file', this.selectedFile);

        this.fraseService.establecerFraseCompartida(this.inputMensage)
        this.nombreFiesta.establecerNombreFiesta(this.inputText)
        this.peticionAddImagen.addImagen(formData, this.nombreFiesta.obtenerNombreFiesta());

        this.router.navigate(['/crear']);
      }
    }

    if (selectedOption === "Eliminar evento") {
      this.nombreFiesta.establecerNombreFiesta(this.selectedOptionCarpeta)
      this.router.navigate(['/eliminar']);
    }

    if (selectedOption === "Limpiar cache") {
        this.peticionEliminarArchivos.getEliminarDatos().subscribe((message) => {
          this.responseData = message;
          if (this.responseData == "Archivos sin extensión eliminados correctamente") {
            this.showPopup(this.responseData);
          }
        }, (error) => {
          this.showPopup(error);
        });

    }
  }

  showPopup(message: string) {
    const popupContainer = this.renderer.createElement('div');
    const popupText = this.renderer.createText(message);

    this.renderer.appendChild(popupContainer, popupText);
    this.renderer.setAttribute(popupContainer, 'id', 'popupContainer');

    this.renderer.listen(popupContainer, 'click', () => {
      this.hidePopup(popupContainer);
    });

    this.renderer.appendChild(document.body, popupContainer);
    this.cdr.detectChanges();

    this.isPopupVisible = true;

    setTimeout(() => {
      this.hidePopup(popupContainer);
    }, 3000);
  }

  hidePopup(popupContainer: any) {
    this.renderer.removeChild(document.body, popupContainer);
    this.cdr.detectChanges();

    this.isPopupVisible = false;
  }

  // Función que determina si el botón debe estar deshabilitado
  isButtonDisabled(): boolean {
    if (this.selectedOption === 'Seleccionar') {
      return true;
    }
    if (
      (this.selectedOption === 'Acceder a evento' || this.selectedOption === 'Eliminar evento') &&
      this.selectedOptionCarpeta !== 'Seleccionar'
    ) {
      return false;
    }
    if (this.selectedOption === 'Crear evento' && this.inputText !== '') {
      return false;
    }
    if (this.selectedOption === 'Limpiar cache') {
      return false;
    }
    return true;
  }
}
