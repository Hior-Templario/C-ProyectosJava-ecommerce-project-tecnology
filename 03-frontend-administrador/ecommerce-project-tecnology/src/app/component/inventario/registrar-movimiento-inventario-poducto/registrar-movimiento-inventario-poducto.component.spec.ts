import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarMovimientoInventarioPoductoComponent } from './registrar-movimiento-inventario-poducto.component';

describe('RegistrarMovimientoInventarioPoductoComponent', () => {
  let component: RegistrarMovimientoInventarioPoductoComponent;
  let fixture: ComponentFixture<RegistrarMovimientoInventarioPoductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarMovimientoInventarioPoductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarMovimientoInventarioPoductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
