import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificaionStickyComponent } from './notificaion-sticky.component';

describe('NotificaionStickyComponent', () => {
  let component: NotificaionStickyComponent;
  let fixture: ComponentFixture<NotificaionStickyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificaionStickyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificaionStickyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
