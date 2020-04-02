import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommonComponentModule } from 'src/app/common/common-component.module';
import { TaskRoutingModule } from "./task-routing.module";
import { TaskCalendarComponent } from './task-calendar/task-calendar.component';
import { TaskCreatorComponent } from './new-task/new-task.component';
import { TaskListComponent } from './my-tasks/task-list.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { TaskComponent } from './task.component';
import { FormsModule } from '@angular/forms';
import { TaskDetailComponent } from './task-detail/task-detail.component';
import { CommentboxComponent } from './comment-box/commentbox.component';
import { CheckListFormComponent } from './check-list-form/checklist-form.component';
import { MatFormFieldModule, MatInputModule } from '@angular/material';
import { CheckListUpdateComponent } from './checklist-update/checklist-update.component';
@NgModule({
    declarations: [
        // TaskCalendarComponent,
         TaskCreatorComponent,
         TaskListComponent,
        PageNotFoundComponent,
        TaskComponent,
        TaskCalendarComponent,
        TaskDetailComponent,
        CommentboxComponent,
        CheckListFormComponent,
        CheckListUpdateComponent
    ],
    imports: [ CommonModule,CommonComponentModule,
        TaskRoutingModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule
     ],
    exports: [],
    providers: [],
    entryComponents:[CheckListFormComponent,CheckListUpdateComponent]
})
export class TaskModule {}