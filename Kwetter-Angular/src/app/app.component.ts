import {Component, Input, OnInit} from '@angular/core';
import {UserService} from './user.service';
import {User} from './domain/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [UserService]
})
export class AppComponent implements OnInit {
  @Input() user: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.userService.getUser(1).subscribe(
        user => {
          this.user = user;
        }
      );
    }
  }
}
