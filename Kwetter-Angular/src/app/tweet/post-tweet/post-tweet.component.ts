import {Component, OnInit} from '@angular/core';
import {Tweet} from '../../domain/tweet';
import {TweetService} from '../../api/tweet.service';

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.scss'],
  providers: [TweetService]
})
export class PostTweetComponent implements OnInit {
  model: Tweet;

  constructor(private tweetService: TweetService) {
  }

  ngOnInit() {
    this.model = new Tweet();
  }

  onSubmit() {
    this.model.date = new Date();
    this.tweetService.postTweet(this.model).subscribe(
      response => {
        this.model = null;
        return true;
      }
    );
  }
}
