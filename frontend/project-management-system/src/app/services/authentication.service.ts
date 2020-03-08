import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../model/user';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { SignupModel } from '../model/signup.model';
import { APIResponse } from '../model/APIResponse';


@Injectable({
    providedIn:'root'
})
export class AuthenticationService {
    private currentUserSubject:BehaviorSubject<User>;
    public currentUser:Observable<User>;
    constructor(private http:HttpClient){
        this.currentUserSubject=new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser=this.currentUserSubject.asObservable();
    }
    
    public get currentUserValue() : User {
        return this.currentUserSubject.value;
    }
    login(username:string,password:string){
        return this.http.post<any>(`${environment.apiUrl}/authentication/login`,{username,password})
        .pipe(map(apiResponse=>{
            localStorage.setItem('currentUser',JSON.stringify(apiResponse.data));
            this.currentUserSubject.next(apiResponse.data);
            return apiResponse.data;
        }));
    }
    logout(){
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
    signup(signupModel:SignupModel):Observable<APIResponse>{
        return this.http.post<APIResponse>(`${environment.apiUrl}/authentication/signup`,signupModel);
    }
}