import { Component, OnInit } from '@angular/core';
import { LoginModel } from 'src/app/model/login.model';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private router: Router, private authenticationService: AuthenticationService) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  handleLogin() {
    this.submitted = true;
   if(this.loginForm.invalid){
     this.submitted=false;
     return;
   }
   this.loading=true;
   this.authenticationService.login(this.loginForm.controls.username.value,this.loginForm.controls.password.value)
   .pipe(first())
   .subscribe(data=>{
     this.router.navigate([this.returnUrl]);
   },
   error=>{
    if(error.status==401){
      this.error='User name or password uncorrect';
    }
     this.loading=false;
   }
   )
  }
  get loginFormControl() {
    return this.loginForm.controls;
  }
}
