import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {User} from '../domain/user';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(id) {
    return this.http.get<User>(`${environment.api}/user/${id}`);
  }
}
