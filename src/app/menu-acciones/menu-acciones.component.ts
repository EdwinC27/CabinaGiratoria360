import { Component, ChangeDetectorRef, Renderer2, OnInit } from '@angular/core';
import { FileService } from '../FileLogoFiesta/file.service';
import { FraseService } from '../Frase/frase.service';
import { Router } from '@angular/router';
import { NombreFiestaService } from '../NombreFiesta/Nombre.Fiesta.Service';
import { PeticionEliminarArchivos } from '../Peticiones-API/EliminarArchivos/eliminarArchivos';
import { MostrarCarpetasService } from '../Peticiones-API/TraerNombresDeCarpetas/videos-fiesta.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PeticionAddImagen } from '../Peticiones-API/SubirImagen/subirImagen';
import { PeticionCrearCarpeta } from '../Peticiones-API/CrearCarpeta/crear';
import { PeticionDescargarCarpeta } from '../Peticiones-API/DescargarCarpeta/descargarCarpeta';
import { TraerLogo } from '../Peticiones-API/TraerLogoUsuario/getLogo.service';

@Component({
  selector: 'app-menu-acciones',
  templateUrl: './menu-acciones.component.html',
  styleUrls: ['./menu-acciones.component.css']
})
export class MenuAccionesComponent implements OnInit {
  // opciones menu
  selectedOption: string = 'Seleccionar';
  selectedOptionCarpeta: string = 'Seleccionar';
  selectedOptionDefault: string = 'Seleccionar';

  // opciones de guardado
  inputText: string = '';
  inputMensage: string = '';
  resultadoImagen: any;

  // respuesta de API
  responseData: any;
  responseCarpetas: any;
  carpetas: any;

  logoRuta = "";

  // mostrar cosas
  isPopupVisible: boolean = false;
  public showOverlay = true;
  showImage: boolean = false;

  selectedFile: File | null = null;

  mensajeEliminacion: string = "";

  currentUser: string | null;

  constructor(private fraseService: FraseService, private fileService: FileService, private nombreFiesta: NombreFiestaService, private router: Router, private peticionEliminarArchivos: PeticionEliminarArchivos, private mostrarCarpetasService: MostrarCarpetasService, private cdr: ChangeDetectorRef, private renderer: Renderer2, private http: HttpClient, private peticionAddImagen: PeticionAddImagen, private peticionCrearCarpeta: PeticionCrearCarpeta, private peticionDescargarCarpeta: PeticionDescargarCarpeta, private logo: TraerLogo) {
    this.currentUser = sessionStorage.getItem('currentUser');
  }

  async seleccionarArchivo(event: any) {
    this.selectedFile = event.target.files[0];

    const imagen = event.target.files[0];
    const resultado = await this.convertirImagenABase64(imagen);
    this.resultadoImagen = resultado.base64
  }

  subirArchivo() {
    this.fileService.establecerFileCompartida(this.resultadoImagen);
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
    if (this.currentUser == null) {
      this.router.navigate(['/login']);
    }

    this.logo.getInfo(this.currentUser).subscribe(
      () => {
        this.logoRuta = this.logo.respuesta.logo;
       },
      (error) => {
        console.log(error);
      }
    )

    this.eliminarCache();

    this.mostrarCarpetasService.getMostrarCarpetas(this.currentUser).subscribe((carpeta) => {
      this.carpetas = carpeta;
    }, (error) => {
      this.showPopup(error);
    });

    setTimeout(() => {
      this.showOverlay = false;
    }, 0);
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
        this.peticionAddImagen.addImagen(formData, this.nombreFiesta.obtenerNombreFiesta(), this.currentUser);

        this.peticionCrearCarpeta.getCrearFiesta(this.nombreFiesta.obtenerNombreFiesta(), this.fraseService.obtenerFraseCompartida(), this.currentUser).subscribe((message) => {
          this.responseData = message;

          this.showImage = true;
          setTimeout(() => {
            this.showImage = false;

            this.router.navigate(['/acceder']);
          }, 7000);

        }, (error) => {
          this.mensajeEliminacion = error;
          this.cdr.detectChanges(); // Detectar cambios para actualizar el HTML
        });
      }
    }

    if (selectedOption === "Eliminar evento") {
      this.nombreFiesta.establecerNombreFiesta(this.selectedOptionCarpeta)
      this.router.navigate(['/eliminar']);
    }

    if (selectedOption === "Descargar evento") {
      this.showImage = true;
      this.peticionDescargarCarpeta.onDownloadFolder(this.selectedOptionCarpeta, sessionStorage.getItem('currentUser'))
        .subscribe(
          (response: any) => {
            // Aquí puedes manejar la respuesta si es necesario
          },
          (error: any) => {
            console.error('Error al descargar el archivo:', error);
            this.showImage = false;
            alert('Error al descargar el archivo');
          },
          () => {
            this.showImage = false;
          }
        );
    }

    /*
    if (selectedOption === "Limpiar cache") {
      this.showImage = true;

        this.peticionEliminarArchivos.getEliminarDatos().subscribe((message) => {
          this.responseData = message;
          this.showImage = false;

          if (this.responseData == "Archivos sin extensión eliminados correctamente") {
            this.showPopup(this.responseData);
          }
        }, (error) => {
          this.showImage = false;

          this.showPopup(error);
        });

    }
    */
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
      (this.selectedOption === 'Acceder a evento' || this.selectedOption === 'Eliminar evento' || this.selectedOption === 'Descargar evento') &&
      this.selectedOptionCarpeta !== 'Seleccionar'
    ) {
      return false;
    }
    if (this.selectedOption === 'Crear evento' && this.inputText !== '' && this.selectedFile !== null) {
      return false;
    }
    if (this.selectedOption === 'Limpiar cache') {
      return false;
    }
    return true;
  }

  eliminarCache(): void {
    this.peticionEliminarArchivos.getEliminarDatos().subscribe((message) => {
      this.responseData = message;
    }, (error) => {
      this.showPopup(error);
    });
  }

}
