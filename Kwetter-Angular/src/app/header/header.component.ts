import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {User} from '../domain/user';
import {AuthenticationService} from '../api/authentication.service';
import {Login} from '../domain/login';
import {ModalDirective} from 'angular-bootstrap-md';
import {UserService} from '../api/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  user: User;
  login: Login;
  search: string;
  error: string;

  @ViewChild('loginModal')
  loginModal: ModalDirective;

  constructor(private authService: AuthenticationService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.loadUser();
    this.authService.loginEvent$.subscribe(() => this.loadUser());

    this.login = new Login();
    this.search = '';
  }

  loadUser() {
    this.userService.getLoggedInUser().subscribe(user => {
      this.user = user;
    });
  }

  doLogin() {
    this.authService.login(this.login.email, this.login.password)
      .subscribe(resp => {
        this.login = new Login();
        this.loginModal.hide();
        this.error = '';
      }, error => {
        this.login.password = '';
        this.error = error.toString();
      });
  }

  logout() {
    this.authService.logout();
    return false;
  }

  doSearch() {

  }
}
