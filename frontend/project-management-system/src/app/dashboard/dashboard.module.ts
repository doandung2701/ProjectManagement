import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskComponent } from './task/task.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { HeaderComponent } from './header/header.component';
import { DashboardComponent } from './dashboard.component';
import { CommonComponentModule } from '../common/common-component.module';
import { SummaryComponent } from './summary/summary.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
@NgModule({
  declarations: [ TaskComponent, HeaderComponent,DashboardComponent, SummaryComponent,PageNotFoundComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    CommonComponentModule
  ]
})
export class DashboardModule { }
