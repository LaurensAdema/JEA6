import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {Tweet} from '../domain/tweet';
import {animate, style, transition, trigger} from '@angular/animations';
import {User} from '../domain/user';
import {TweetService} from '../api/tweet.service';
import {Flag} from '../domain/flag';
import {ModalDirective} from 'angular-bootstrap-md';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.scss'],
  animations: [
    trigger('animateTweet', [
      transition(':enter', [
        style({transform: 'scale(0.5)', opacity: 0, height: 0, overflow: 'hidden'}),  // initial
        animate('1s cubic-bezier(.8, -0.6, 0.2, 1.5)',
          style({transform: 'scale(1)', opacity: 1, height: '*'}))  // final
      ]),
      transition(':leave', [
        style({transform: 'scale(1)', opacity: 1, height: '*'}),
        animate('1s cubic-bezier(.8, -0.6, 0.2, 1.5)',
          style({
            transform: 'scale(0.5)', opacity: 0,
            height: '0px', margin: '0px', overflow: 'hidden'
          }))
      ])
    ])
  ]
})
export class TweetComponent implements OnInit, OnChanges {
  @Input() tweet: Tweet;
  @Input() user: User;

  likedByMe: boolean;
  flaggedByMe: boolean;
  flag: Flag;

  @ViewChild('flagModal')
  flagModal: ModalDirective;

  constructor(private tweetService: TweetService) {
    this.flag = new Flag();
  }

  like() {
    this.tweetService.likeTweet(this.tweet).subscribe(tweet => {
      this.tweet = tweet;
      this.getLikedByMe();
    });
  }

  handleFlag() {
    if (this.flag.reason) {
      this.flag.date = new Date();
      this.tweetService.flagTweet(this.tweet.id, this.flag).subscribe(tweet => {
        this.tweet = tweet;
        this.flag = new Flag();
        this.flagModal.hide();
        this.getFlaggedByMe();
      });
    }
  }

  getLikedByMe() {
    if (this.user && this.tweet.likes.find(x => x.id === this.user.id)) {
      this.likedByMe = true;
    } else {
      this.likedByMe = false;
    }
  }

  getFlaggedByMe() {
    if (this.user && this.tweet.flags.find(x => x.id === this.user.id)) {
      this.flaggedByMe = true;
    } else {
      this.flaggedByMe = false;
    }
  }

  ngOnInit(): void {
    this.getLikedByMe();
    this.getFlaggedByMe();
    if (this.flagModal) {
      this.flagModal.onHide.subscribe(e => this.flag = new Flag());
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.getLikedByMe();
    this.getFlaggedByMe();
  }
}
