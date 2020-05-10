import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from "@angular/flex-layout";
import { FullCalendarModule } from 'primeng/fullcalendar'; // for FullCalendar!
import {ChartModule} from 'primeng/chart';

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
   MatSnackBarModule,
   MatSortModule,
   MatProgressSpinnerModule,
   MatCheckboxModule,
} from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MainNavComponent } from './main-nav/main-nav.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NotificationService } from './service/notification.sevice';
import {PickListModule} from 'primeng/picklist';
import {  KeysPipe } from './custompipe/Keys.pipe';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';


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
      MatSortModule,
      MatProgressSpinnerModule,
      MatCheckboxModule ,
      FullCalendarModule,
      PickListModule,
      ChartModule
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
      FullCalendarModule,
      MatSortModule,
      MatProgressSpinnerModule,
      MatCheckboxModule,
      PickListModule,
      KeysPipe,
      ChartModule
   ],
   providers: [
      MatDatepickerModule,
      NotificationService
   ],
   declarations: [MainNavComponent,PageNotFoundComponent,KeysPipe,ConfirmationDialogComponent ],
   entryComponents:[ConfirmationDialogComponent]
})

export class CommonComponentModule { }