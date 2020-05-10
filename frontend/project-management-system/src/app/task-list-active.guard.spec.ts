import { TestBed, async, inject } from '@angular/core/testing';

import { TaskListActiveGuard } from './task-list-active.guard';

describe('TaskListActiveGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TaskListActiveGuard]
    });
  });

  it('should ...', inject([TaskListActiveGuard], (guard: TaskListActiveGuard) => {
    expect(guard).toBeTruthy();
  }));
});
