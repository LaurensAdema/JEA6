import {Component, Input, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {TweetService} from '../api/tweet.service';
import {AuthenticationService} from '../api/authentication.service';
import {UserService} from '../api/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [AuthenticationService, UserService]
})
export class HeaderComponent implements OnInit {
  user: User;

  constructor(private authService: AuthenticationService, private userService: UserService) { }

  ngOnInit() {
    this.userService.getLoggedInUser().subscribe(
      user => {
        this.user = user;
      }
    );
  }

  logout() {
    localStorage.setItem('userId', '');
  }
}
