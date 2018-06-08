import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from '../domain/user';
import {AuthenticationService} from '../api/authentication.service';
import {UserService} from '../api/user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Login} from '../domain/login';
import {ModalDirective} from 'angular-bootstrap-md';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  user: User;
  login: Login;
  search: string;
  @ViewChild('loginModal')
  loginModal: ModalDirective;

  constructor(private authService: AuthenticationService, private userService: UserService, private jwtService: JwtHelperService) { }

  ngOnInit(): void {
    this.login = new Login();
    this.search = '';
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
      localStorage.removeItem('access_token');
    }
  }

  doLogin() {
    this.authService.login(this.login.email, this.login.password)
      .subscribe(resp => {
        this.loginModal.hide();
      });
  }

  logout() {
    this.authService.logout();
    return false;
  }

  doSearch() {

  }
}
