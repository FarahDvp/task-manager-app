import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAllTasksUserComponent } from './list-all-tasks-user.component';

describe('ListAllTasksUserComponent', () => {
  let component: ListAllTasksUserComponent;
  let fixture: ComponentFixture<ListAllTasksUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListAllTasksUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListAllTasksUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
