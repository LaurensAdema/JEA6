import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Tweet} from '../domain/tweet';
import {TweetService} from '../api/tweet.service';
import {SocketService} from '../api/socket.service';
import {animateChild, query, transition, trigger} from '@angular/animations';
import {User} from '../domain/user';
import {UserService} from '../api/user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AuthenticationService} from '../api/authentication.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss'],
  providers: [TweetService],
  animations: [
    trigger('ngIfAnimation', [
      transition(':enter, :leave', [
        query('@*', animateChild())
      ])
    ])
  ]
})
export class TimelineComponent implements OnInit, OnChanges {
  @Input() timelineUser: User;
  @Input() blockTimeline: boolean;

  user: User;
  tweets: Tweet[] = [];

  constructor(private tweetService: TweetService, private socketService: SocketService,
              private userService: UserService, private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.loadUser();
    this.authService.loginEvent$.subscribe(() => { this.loadUser(); });
  }

  loadUser() { this.userService.getLoggedInUser().subscribe(user => { this.user = user; this.loadTweets(); } ); }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadTweets();
  }

  loadTweets() {
    if (!this.blockTimeline) {
      let fetchTweets;
      if (this.timelineUser) {
        fetchTweets = this.tweetService.getTweetsOf(this.timelineUser.id);
        this.socketService.changePage(this.timelineUser.id.toString());
      } else if (this.user) {
        fetchTweets = this.tweetService.getTweetsForMe();
        this.socketService.changePage('');
      } else {
        fetchTweets = this.tweetService.getAllTweets();
        this.socketService.changePage('');
      }

      fetchTweets.subscribe(tweets => {
        tweets.forEach(tweet => {
          this.handleTweet(tweet);
        });
      });
      this.socketService.tweetUpdated$.subscribe(tweet => {
        this.handleTweet(tweet);
      });
    }
  }

  handleTweet(tweet: Tweet) {
    const existingTweet = this.tweets.find(x => x.id === tweet.id);
    if (!tweet.message && !tweet.user && existingTweet) {
      this.deleteTweet(existingTweet);
    } else if (!existingTweet) {
      this.addTweet(tweet);
    } else {
      this.updateTweet(existingTweet, tweet);
    }
    // this.cleanUp();
  }

  addTweet(toAdd: Tweet) {
    this.tweets.unshift(toAdd);
  }

  updateTweet(existingTweet: Tweet, toUpdate: Tweet) {
    console.log(`Updated tweet with id ${toUpdate.id}`);
    Object.assign(existingTweet, existingTweet, toUpdate);
  }

  deleteTweet(toDelete: Tweet) {
    console.log(`Deleted tweet with id ${toDelete.id}`);
    const index = this.tweets.indexOf(toDelete);
    this.tweets.splice(index, 1);
  }

  cleanUp() {
    if (this.tweets.length > 1) {
      this.tweets.splice(1);
    }
  }
}
