import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {User} from '../domain/user';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(id) {
    return this.http.get<User>(`${environment.api_protocol}${environment.api_domain}/user/${id}`);
  }

  getLoggedInUser() {
    return this.http.get<User>(`${environment.api_protocol}${environment.api_domain}/user/me`);
  }

  getAllUsers() {
    return this.http.get<User[]>(`${environment.api_protocol}${environment.api_domain}/user`);
  }
}
