import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectComponent } from './project/project.component';
import { TaskComponent } from './task/task.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { HeaderComponent } from './header/header.component';
import { DashboardComponent } from './dashboard.component';
import { CommonComponentModule } from '../common/common-component.module';
import { SummaryComponent } from './summary/summary.component';
import { ProjectItemComponent } from './project-item/project-item.component';



@NgModule({
  declarations: [ProjectComponent, TaskComponent, HeaderComponent,DashboardComponent, SummaryComponent,ProjectItemComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    CommonComponentModule
  ]
})
export class DashboardModule { }
