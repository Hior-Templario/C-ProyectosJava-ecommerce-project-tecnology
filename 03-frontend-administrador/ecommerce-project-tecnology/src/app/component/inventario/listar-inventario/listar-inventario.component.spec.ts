import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListarInventarioComponent } from './listar-inventario.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

describe('ProductoComponent', () => {
  let component: ListarInventarioComponent;
  let fixture: ComponentFixture<ListarInventarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormsModule, CommonModule],
      declarations: [ListarInventarioComponent]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('debería crear el componente', () => {
    expect(component).toBeTruthy();
  });
});