import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HidenTasksAdminComponent } from './hiden-tasks-admin.component';

describe('HidenTasksAdminComponent', () => {
  let component: HidenTasksAdminComponent;
  let fixture: ComponentFixture<HidenTasksAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HidenTasksAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HidenTasksAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
