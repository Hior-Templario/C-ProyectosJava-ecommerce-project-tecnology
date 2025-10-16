import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallesMarcaComponent } from './detalles-marca.component';

describe('DetallesMarcaComponent', () => {
  let component: DetallesMarcaComponent;
  let fixture: ComponentFixture<DetallesMarcaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetallesMarcaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetallesMarcaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
