import {Component, Input, OnInit} from '@angular/core';
import {Tweet} from '../../domain/tweet';
import {TweetService} from '../../api/tweet.service';
import {User} from '../../domain/user';
import {Observable} from 'rxjs';

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
  }

  onSubmit() {
    this.model.date = new Date();
    this.model.user = this.user;
    console.log(JSON.stringify(this.model));
    this.tweetService.postTweet(this.model).subscribe(
      response => {
        console.log(response);
      }
    );
  }
}
