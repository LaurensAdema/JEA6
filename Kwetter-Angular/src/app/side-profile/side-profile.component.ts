import {Component, Input, OnInit} from '@angular/core';
import {User} from '../domain/user';

@Component({
  selector: 'app-side-profile',
  templateUrl: './side-profile.component.html',
  styleUrls: ['./side-profile.component.scss']
})
export class SideProfileComponent {
  @Input() user: User;

  constructor() { }
}
