import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccederCarpetaComponent } from './acceder-carpeta.component';

describe('AccederCarpetaComponent', () => {
  let component: AccederCarpetaComponent;
  let fixture: ComponentFixture<AccederCarpetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccederCarpetaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccederCarpetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
