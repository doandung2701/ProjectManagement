import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TaskComponent } from './task.component';
import { TaskCreatorComponent } from './new-task/new-task.component';
import { TaskListComponent } from './my-tasks/task-list.component';
import { FullCalendarComponent } from '@fullcalendar/angular';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

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
        path:'calendar',
        component:FullCalendarComponent
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
