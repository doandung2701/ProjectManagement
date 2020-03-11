import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectComponent } from './project.component';
import { ProjectCreateComponent } from './project-create/project-create.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { EnterProjectComponent } from './project-enter/enter-project.component';
import { CommonComponentModule } from 'src/app/common/common-component.module';
import { ProjectRoutingModule } from './project-routing.module';
import { FormsModule } from '@angular/forms';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

@NgModule({
    declarations: [ProjectCreateComponent,ProjectComponent,ProjectListComponent,EnterProjectComponent,PageNotFoundComponent],
    imports: [ CommonModule ,CommonComponentModule,ProjectRoutingModule,FormsModule],
    exports: [],
    providers: [],
})
export class ProjectModule {}