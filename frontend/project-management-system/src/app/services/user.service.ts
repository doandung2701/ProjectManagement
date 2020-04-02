import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { UserListDto } from '../model/listUserDto.model';
import { APIPaginationResponse } from '../model/APIPaginationResponse.model';
import { Observable } from 'rxjs';
import { UserDto } from '../model/userDto.model';

@Injectable({
    providedIn:'root'
})
export class UserService {
    // url=environment.apiUrl;
    // url="http://localhost:7010";
    private url=environment.apiUrl;;
    constructor(private http:HttpClient){}
    findEmail(email:string,pageNumber:number,pageSize:number):Observable<APIPaginationResponse<UserDto>>{
      return  this.http.get<APIPaginationResponse<UserDto>>(this.url+`/user/findByEmail/${email}?page=${pageNumber}&&size=${pageSize}`);
    }
}