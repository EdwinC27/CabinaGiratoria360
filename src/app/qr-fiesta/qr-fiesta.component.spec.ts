import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QrFiestaComponent } from './qr-fiesta.component';

describe('QrFiestaComponent', () => {
  let component: QrFiestaComponent;
  let fixture: ComponentFixture<QrFiestaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QrFiestaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QrFiestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
