import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeleccionarFiestaComponent } from './seleccionar-fiesta.component';

describe('SeleccionarFiestaComponent', () => {
  let component: SeleccionarFiestaComponent;
  let fixture: ComponentFixture<SeleccionarFiestaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SeleccionarFiestaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SeleccionarFiestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
