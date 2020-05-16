import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Top5taskdeadlineComponent } from './top5taskdeadline.component';

describe('Top5taskdeadlineComponent', () => {
  let component: Top5taskdeadlineComponent;
  let fixture: ComponentFixture<Top5taskdeadlineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Top5taskdeadlineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Top5taskdeadlineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
