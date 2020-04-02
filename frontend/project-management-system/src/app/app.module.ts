import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatProgressBarModule, MatProgressSpinnerModule } from '@angular/material';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { AuthenticationService } from './services/authentication.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoaderService } from './loader.service';
import { LoaderInterceptor } from './loader.interceptor';
import { LoaderComponent } from './loader.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ProjectService } from './services/project.service';
import { UserService } from './services/user.service';
import { GlobalService } from './services/global.service';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { CorsHeaderInterceptor } from './helpers/cors.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoaderComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatProgressBarModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    HttpClientModule,
    MatProgressBarModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true
  },{
    provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true
  },{
    provide:HTTP_INTERCEPTORS,useClass:CorsHeaderInterceptor,multi:true
  }, LoaderService, AuthenticationService,ProjectService,UserService,GlobalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
