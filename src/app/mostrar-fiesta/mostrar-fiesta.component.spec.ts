import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MostrarFiestaComponent } from './mostrar-fiesta.component';

describe('MostrarFiestaComponent', () => {
  let component: MostrarFiestaComponent;
  let fixture: ComponentFixture<MostrarFiestaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MostrarFiestaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MostrarFiestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
