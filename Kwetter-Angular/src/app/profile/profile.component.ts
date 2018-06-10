import {Component, Input, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {Tweet} from '../domain/tweet';
import {ActivatedRoute, Params} from '@angular/router';
import {UserService} from '../api/user.service';
import {TweetService} from '../api/tweet.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileUser: User;
  blockTimeline: boolean;

  constructor(private route: ActivatedRoute, private userService: UserService) {
    this.blockTimeline = true;
  }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      if (params['query']) {
        this.userService.getUser(params['query']).subscribe(user => {
          this.profileUser = user;
          this.blockTimeline = false;
        });
      }
    });
  }
}
