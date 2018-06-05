import {Component, Input, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {Tweet} from '../domain/tweet';
import {ActivatedRoute, Params} from '@angular/router';
import {UserService} from '../api/user.service';
import {TweetService} from '../api/tweet.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [TweetService]
})
export class ProfileComponent implements OnInit {
  @Input() user: User;
  tweets: Tweet[];

  constructor(private route: ActivatedRoute, private userService: UserService, private tweetService: TweetService) {}

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      if (params['query']) {
        this.loadUser(params['query']);
      }
    });
  }

  loadUser(query) {
    this.userService.getUser(query).subscribe(user => {
      this.user = user;
      this.loadTweets();
    });
  }

  loadTweets() {
    this.tweetService.getTweetsOf(this.user.id).subscribe(tweets => {
      this.tweets = tweets;
    });
  }
}
