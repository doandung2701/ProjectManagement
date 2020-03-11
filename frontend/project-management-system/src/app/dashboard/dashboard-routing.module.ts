import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';
import { DashboardComponent } from './dashboard.component';
import { SummaryComponent } from './summary/summary.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
    {
        path:'',
        component:DashboardComponent,
        children:[
            {
                path:'project',
                loadChildren:()=>import('./project/project.module').then(m=>m.ProjectModule)
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
            },
            {
                path:'**',
                component:PageNotFoundComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DashboardRoutingModule { }
