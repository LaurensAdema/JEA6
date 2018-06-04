import {Component, Input, OnInit} from '@angular/core';
import {User} from '../domain/user';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() user: User;

  constructor() { }

  logout() {
    localStorage.setItem('userId', '');
  }
}
