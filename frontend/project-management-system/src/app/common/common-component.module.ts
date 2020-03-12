import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from "@angular/flex-layout";
import { FullCalendarModule } from '@fullcalendar/angular'; // for FullCalendar!

import {
   MatButtonModule,
   MatToolbarModule,
   MatIconModule,
   MatBadgeModule,
   MatSidenavModule,
   MatListModule,
   MatGridListModule,
   MatFormFieldModule,
   MatInputModule,
   MatSelectModule,
   MatRadioModule,
   MatDatepickerModule,
   MatNativeDateModule,
   MatChipsModule,
   MatTooltipModule,
   MatTableModule,
   MatPaginatorModule,
   MatCardModule,
   MatMenuModule,
   MatTabsModule,
   MatProgressSpinner,
   MatProgressBarModule,
   MatDialogModule,
   MatStepperModule,
   MatAutocompleteModule,
   MatSnackBarModule
} from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MainNavComponent } from './main-nav/main-nav.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NotificationService } from './service/notification.sevice';

@NgModule({
   imports: [
      CommonModule,
      MatButtonModule,
      MatToolbarModule,
      MatIconModule,
      MatSidenavModule,
      MatBadgeModule,
      MatListModule,
      MatGridListModule,
      MatFormFieldModule,
      MatInputModule,
      MatSelectModule,
      MatRadioModule,
      MatDatepickerModule,
      MatNativeDateModule,
      MatChipsModule,
      MatTooltipModule,
      MatTableModule,
      MatPaginatorModule,
      MatCardModule,
      FlexLayoutModule,
      ReactiveFormsModule,
      MatMenuModule,
      RouterModule,MatTabsModule,
      MatProgressBarModule,
      MatMenuModule,
      MatDialogModule,
      MatStepperModule,
      MatAutocompleteModule,
      MatSnackBarModule,
      FullCalendarModule
   ],
   exports: [
     
      MatButtonModule,
      MatToolbarModule,
      MatIconModule,
      MatSidenavModule,
      MatBadgeModule,
      MatListModule,
      MatGridListModule,
      MatFormFieldModule,
      MatInputModule,
      MatSelectModule,
      MatRadioModule,
      MatDatepickerModule,
      MatNativeDateModule,
      MatChipsModule,
      MatTooltipModule,
      MatTableModule,
      MatPaginatorModule,
      MatCardModule,
      FlexLayoutModule,
      ReactiveFormsModule,
      MatMenuModule,
      MainNavComponent,
      MatTabsModule,
      MatProgressBarModule,
      MatMenuModule,
      MatStepperModule,
      MatAutocompleteModule,
      MatDialogModule,
      PageNotFoundComponent,
      MatSnackBarModule,
      FullCalendarModule
      
   ],
   providers: [
      MatDatepickerModule,
      NotificationService
   ],
   declarations: [MainNavComponent,PageNotFoundComponent ]
})

export class CommonComponentModule { }