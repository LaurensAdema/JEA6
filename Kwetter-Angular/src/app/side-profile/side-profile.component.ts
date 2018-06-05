import {Component, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {UserService} from '../api/user.service';

@Component({
  selector: 'app-side-profile',
  templateUrl: './side-profile.component.html',
  styleUrls: ['./side-profile.component.scss'],
  providers: [UserService]
})
export class SideProfileComponent implements OnInit{
  user: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getLoggedInUser().subscribe(
      user => {
        this.user = user;
      }
    );
  }
}
