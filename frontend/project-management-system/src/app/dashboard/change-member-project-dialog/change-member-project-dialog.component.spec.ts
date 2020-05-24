import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeMemberProjectDialogComponent } from './change-member-project-dialog.component';

describe('ChangeMemberProjectDialogComponent', () => {
  let component: ChangeMemberProjectDialogComponent;
  let fixture: ComponentFixture<ChangeMemberProjectDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeMemberProjectDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeMemberProjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
