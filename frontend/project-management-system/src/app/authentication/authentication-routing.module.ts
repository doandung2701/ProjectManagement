import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { AuthenticationComponent } from './authentication.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
 {
   path:'',
   component:AuthenticationComponent,
   children:[
    {
      path: 'login',
      component: LoginComponent,
    },
    {
      path:'signup',
      component:SignupComponent
    },
    {
      path:'',redirectTo:'login',
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
export class AuthenticationRoutingModule { }
