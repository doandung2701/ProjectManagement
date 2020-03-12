import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserListDto } from '../model/listUserDto.model';

@Injectable({
    providedIn:'root'
})
export class UserService {
    url=environment.apiUrl;
    constructor(private http:HttpClient){}
    findEmail(email:string){
      return  this.http.get<UserListDto>(this.url+`/user/findByEmail/${email}`);
    }
}