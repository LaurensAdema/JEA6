import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Token} from '../domain/token';
import {Subject} from 'rxjs';

@Injectable()
export class AuthenticationService {
  private loginEvent = new Subject();
  loginEvent$ = this.loginEvent.asObservable();

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    const loginRequest = this.http.post<Token>(`${environment.api_protocol}${environment.api_domain}/auth/login`, {email: email, password: password});
    loginRequest.subscribe(resp => {
      localStorage.setItem('access_token', resp.accessToken);
      this.loginEvent.next();
    });
    return loginRequest;
  }

  logout() {
    localStorage.removeItem('access_token');
    this.loginEvent.next();
  }
}
