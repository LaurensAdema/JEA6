import {Component, Input, OnInit} from '@angular/core';
import {Tweet} from '../../domain/tweet';
import {TweetService} from '../../tweet.service';
import {User} from '../../domain/user';

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.scss'],
  providers: [TweetService]
})
export class PostTweetComponent implements OnInit {
  @Input() user: User;
  model: Tweet;

  constructor(private tweetService: TweetService) {
  }

  ngOnInit() {
    this.model = new Tweet();
    this.model.user = this.user;
  }

  onSubmit() {
    this.model.date = new Date();
  }
}
