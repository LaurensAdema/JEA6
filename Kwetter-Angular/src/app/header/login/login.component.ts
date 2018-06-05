import { Component, OnInit } from '@angular/core';
import {UserService} from '../../api/user.service';
import {User} from '../../domain/user';
import {AuthenticationService} from '../../api/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [AuthenticationService, UserService]
})
export class LoginComponent implements OnInit {
  user: User;

  constructor(private authService: AuthenticationService, private userService: UserService) { }

  ngOnInit() {
    this.user = new User();
    this.userService.getLoggedInUser().subscribe(
      user => {
        if (user != null) {
          this.user = user;
        }
      }
    );
  }

  doLogin() {
    this.authService.login(this.user.email, this.user.password)
      .subscribe(resp => {
        localStorage.setItem('access_token', resp.accessToken);
      });
  }
}
