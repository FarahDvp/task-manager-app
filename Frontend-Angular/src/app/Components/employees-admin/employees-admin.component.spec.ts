import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeesAdminComponent } from './employees-admin.component';

describe('EmployeesAdminComponent', () => {
  let component: EmployeesAdminComponent;
  let fixture: ComponentFixture<EmployeesAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployeesAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeesAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
