import { Component } from '@angular/core';
import {UserService} from './services/user.service';
import {TweetService} from './services/tweet.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [TweetService, UserService]
})
export class AppComponent {
  user;

  constructor(private tweetService: TweetService, private userService: UserService){}

  ngOnInit() {

  }
}
