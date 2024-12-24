import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListProjetUserComponent } from './list-projet-user.component';

describe('ListProjetUserComponent', () => {
  let component: ListProjetUserComponent;
  let fixture: ComponentFixture<ListProjetUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListProjetUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListProjetUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
