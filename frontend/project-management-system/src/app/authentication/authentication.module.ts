import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { AuthenticationRoutingModule } from './authentication-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonComponentModule } from '../common/common-component.module';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';


@NgModule({
  declarations: [LoginComponent, SignupComponent, HeaderComponent,AuthenticationComponent,PageNotFoundComponent],
  imports: [
    CommonModule,
    AuthenticationRoutingModule,
    FormsModule,
    CommonComponentModule,
    ReactiveFormsModule,
    RouterModule
  ]
})
export class AuthenticationModule { }
