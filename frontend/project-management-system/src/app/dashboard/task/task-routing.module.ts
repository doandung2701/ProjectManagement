import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TaskComponent } from './task.component';
import { TaskCreatorComponent } from './new-task/new-task.component';
import { TaskListComponent } from './my-tasks/task-list.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { TaskCalendarComponent } from './task-calendar/task-calendar.component';
import { TaskDetailComponent } from './task-detail/task-detail.component';

const routes: Routes = [
    { path: '', component: TaskComponent,
children:[
    {
        path:'createTask',
        component:TaskCreatorComponent
    },
    {
        path:'myTask',
        component:TaskListComponent
    },
    {
        path:'myCalendar',
        component:TaskCalendarComponent
    },
    {
        path:'detail/:taskId',
        component:TaskDetailComponent
    },
    {
        path:'',
        redirectTo:'myTask',
        pathMatch:'full'
    },
    {
        pathMatch:'**',
        component:PageNotFoundComponent
    }
] }
];

@NgModule({
    imports: [CommonModule, RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TaskRoutingModule {}
