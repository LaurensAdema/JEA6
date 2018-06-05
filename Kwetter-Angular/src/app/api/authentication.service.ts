import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Token} from '../domain/token';

@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    return this.http.post<Token>(`${environment.api}/auth/login`, {email: email, password: password});
  }
}
