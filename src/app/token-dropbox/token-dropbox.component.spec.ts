import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TokenDropboxComponent } from './token-dropbox.component';

describe('TokenDropboxComponent', () => {
  let component: TokenDropboxComponent;
  let fixture: ComponentFixture<TokenDropboxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TokenDropboxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TokenDropboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
