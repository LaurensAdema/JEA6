import {Component, Input} from '@angular/core';
import {User} from '../../domain/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent {
  @Input() componentUser: User;

  constructor() { }
}
