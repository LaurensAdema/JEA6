import {Component} from '@angular/core';
import {UserService} from './api/user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AuthenticationService} from './api/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [AuthenticationService, UserService, JwtHelperService]
})
export class AppComponent {}


