import {Component, Input, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {AuthenticationService} from '../api/authentication.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {UserService} from '../api/user.service';
import {SocketService} from '../api/socket.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  user: User;

  constructor(private authService: AuthenticationService, private userService: UserService,
              private jwtService: JwtHelperService, private socketService: SocketService) { }

  ngOnInit(): void {
    this.loadUser();
    this.socketService.startListener();
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
      localStorage.removeItem('access_token');
    }
  }

}
