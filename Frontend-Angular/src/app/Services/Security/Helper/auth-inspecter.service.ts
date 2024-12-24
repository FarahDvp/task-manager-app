import { Injectable } from '@angular/core';
import {TokenStorageService} from "../token-storage.service";
import {HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
const TOKEN_HEADER_KEY = 'Authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthInspecterService {

  constructor(private token: TokenStorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.token.getToken();

    if (token) {
      req = req.clone({
        setHeaders: {
          [TOKEN_HEADER_KEY]: `Bearer ${token}`
        }
      });
    } else {
      req = req.clone({
        headers: req.headers.delete(TOKEN_HEADER_KEY)
      });
    }

    return next.handle(req);
  }
/*  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const token = this.token.getToken();
    //existe token
    if (token != null) {
      authReq= req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
      return next.handle(authReq);
    }
    //pas de token
    else if (token == null){
      // delete req.headers['Authorization'];
      req.headers.delete('Authorization');
      return next.handle(req);
    }
    //token expiré:situation inattendue où le token est ni nul ni non nul.
    else {
      authReq= req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
      return next.handle(authReq);
    }
  }*/


}
export const authInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInspecterService, multi: true}
];
