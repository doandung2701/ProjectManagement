import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';
import { ProjectComponent } from './project/project.component';
import { DashboardComponent } from './dashboard.component';
import { SummaryComponent } from './summary/summary.component';

const routes: Routes = [
    {
        path:'',
        component:DashboardComponent,
        children:[
            {
                path:'project',
                component:ProjectComponent
            },
            {
                path:'task',
                component:TaskComponent
            },
            {
                path:'summary',
                component:SummaryComponent
            },
            {
                path:'',
                redirectTo:'summary',
                pathMatch:'full'
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DashboardRoutingModule { }
