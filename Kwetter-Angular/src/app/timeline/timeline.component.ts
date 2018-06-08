import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Tweet} from '../domain/tweet';
import {TweetService} from '../api/tweet.service';
import {User} from '../domain/user';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss'],
  providers: [TweetService]
})
export class TimelineComponent implements OnInit, OnDestroy {
  tweets: Tweet[] = [];
  ids = [];
  timer;

  constructor(private tweetService: TweetService) {
  }

  ngOnInit() {
    this.getTweets();
    // this.timer = setInterval(() => this.getTweets(), 1000);
  }

  ngOnDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }

  getTweets() {
    this.tweetService.getAllTweets().subscribe(tweets => {
      tweets.forEach(tweet => {
        if (this.ids.indexOf(tweet.id) < 0) {
          this.ids.push(tweet.id);
          this.tweets.unshift(tweet);
        }
      });
      this.cleanUp();
    });
  }

  cleanUp() {
    if (this.tweets.length > 1000) {
      this.tweets.splice(1000);
      this.ids.splice(1000);
    }
  }
}
