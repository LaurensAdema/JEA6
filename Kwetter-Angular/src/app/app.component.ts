import {Component, OnInit} from '@angular/core';
import {UserService} from './api/user.service';
import {User} from './domain/user';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AuthenticationService} from './api/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [AuthenticationService, UserService, JwtHelperService]
})
export class AppComponent implements OnInit {
  user: User;

  constructor(private authService: AuthenticationService, private userService: UserService, private jwtService: JwtHelperService) { }

  ngOnInit(): void {
    this.loadUser();
    this.authService.loginEvent$.subscribe(() => this.loadUser());
  }

  loadUser() {
    if (!this.jwtService.isTokenExpired()) {
      this.userService.getLoggedInUser().subscribe(
        user => {
          this.user = user;
        }
      );
    } else {
      this.user = null;
    }
  }
}
