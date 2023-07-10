import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EliminarCarpetaComponent } from './eliminar-carpeta.component';

describe('EliminarCarpetaComponent', () => {
  let component: EliminarCarpetaComponent;
  let fixture: ComponentFixture<EliminarCarpetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EliminarCarpetaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EliminarCarpetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
