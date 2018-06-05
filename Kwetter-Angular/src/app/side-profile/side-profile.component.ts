import {Component, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {UserService} from '../api/user.service';
import {AuthenticationService} from '../api/authentication.service';
import {JwtHelperService} from '@auth0/angular-jwt';

@Component({
  selector: 'app-side-profile',
  templateUrl: './side-profile.component.html',
  styleUrls: ['./side-profile.component.scss']
})
export class SideProfileComponent implements OnInit {
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
