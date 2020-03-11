import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProjectComponent } from './project.component';
import { ProjectCreateComponent } from './project-create/project-create.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { EnterProjectComponent } from './project-enter/enter-project.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
    { path: '', component: ProjectComponent,
children:[
    {
        path:'createProject',
        component:ProjectCreateComponent
    },
    {
        path:'joinProject',
        component:EnterProjectComponent,

    },
        {
        path:'joinedProject',
        component:ProjectListComponent
        },
    {
        path:'',
        redirectTo:'joinedProject',
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
export class ProjectRoutingModule {}
