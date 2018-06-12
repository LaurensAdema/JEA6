import {Component, Input, OnInit} from '@angular/core';
import {Tweet} from '../../domain/tweet';
import {TweetService} from '../../api/tweet.service';
import {User} from '../../domain/user';
import {UserService} from '../../api/user.service';
import {AuthenticationService} from '../../api/authentication.service';

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.scss'],
  providers: [TweetService]
})
export class PostTweetComponent implements OnInit {
  user: User;
  model: Tweet;

  constructor(private tweetService: TweetService, private userService: UserService, private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.loadUser();
    this.authService.loginEvent$.subscribe(() => this.loadUser());

    this.model = new Tweet();
  }

  loadUser() { this.userService.getLoggedInUser().subscribe(user => { this.user = user; } ); }

  onSubmit() {
    if (this.model.message) {
      this.model.date = new Date();
      this.tweetService.postTweet(this.model).subscribe(
        response => {
          this.model = new Tweet();
          return true;
        }
      );
    }
  }
}
