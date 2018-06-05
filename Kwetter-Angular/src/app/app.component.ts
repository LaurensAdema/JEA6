import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {UserService} from './api/user.service';
import {User} from './domain/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [UserService]
})
export class AppComponent {
  constructor(private userService: UserService) { }
}
