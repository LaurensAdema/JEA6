import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {User} from '../domain/user';
import {JwtHelperService} from '@auth0/angular-jwt';
import 'rxjs/add/observable/of';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private jwtService: JwtHelperService) { }

  getUser(id) {
    return this.http.get<User>(`${environment.api_protocol}${environment.api_domain}/user/${id}`);
  }

  getLoggedInUser() {
    if (!this.jwtService.isTokenExpired()) {
      return this.http.get<User>(`${environment.api_protocol}${environment.api_domain}/user/me`);
    } else {
      localStorage.removeItem('access_token');
      return Observable.of(null);
    }
  }

  getAllUsers() {
    return this.http.get<User[]>(`${environment.api_protocol}${environment.api_domain}/user`);
  }
}
