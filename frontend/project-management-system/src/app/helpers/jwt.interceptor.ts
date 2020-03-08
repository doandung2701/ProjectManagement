import { Injectable } from '@angular/core';
import {
    HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }
    intercept(req: HttpRequest<any>, next: HttpHandler) {
        let currentUser = this.authenticationService.currentUserValue;
        if (currentUser && currentUser.accessToken) {
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${currentUser.accessToken}`
                }
            });
        }

        return next.handle(req);
    }
}