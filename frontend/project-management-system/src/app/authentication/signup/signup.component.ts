import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { SignupModel } from 'src/app/model/signup.model';
import { APIStatus } from 'src/app/model/APIResponse';
import { Router } from '@angular/router';

@Component({
  selector: 'auth-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  singupForm:FormGroup;
  loading = false;
  submitted = false;
  error = '';
  constructor(private router: Router,private authenticationService:AuthenticationService,private fb:FormBuilder) { }

  ngOnInit() {
    this.singupForm=this.fb.group({
      name:['',Validators.compose([Validators.minLength(3),Validators.maxLength(50),Validators.required])],
      username:['',Validators.compose([Validators.minLength(3),Validators.maxLength(50),Validators.required])],
      email:['',Validators.compose([Validators.email,Validators.maxLength(60),Validators.required])],
      password:['',Validators.compose([Validators.minLength(6),Validators.maxLength(40),Validators.required])]
    })
  }
  handlerSignup(){
    this.submitted=true;
    if(this.singupForm.invalid){
      this.submitted=false;
      return;
    }
    this.loading=true;
    let signupModel:SignupModel=new SignupModel();
    signupModel.email=this.signupFormControl.email.value;
    signupModel.name=this.signupFormControl.name.value;
    signupModel.password=this.signupFormControl.password.value;
    signupModel.username=this.signupFormControl.username.value;
    this.authenticationService.signup(signupModel).subscribe(res=>{
        this.router.navigate(["/auth/login"]);
    },error=>{
      this.error=error.error.data;
      this.loading=false;
    })
  }
  get signupFormControl() {
    return this.singupForm.controls;
  }
}
