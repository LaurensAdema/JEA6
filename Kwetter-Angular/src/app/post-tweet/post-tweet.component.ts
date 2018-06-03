import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.scss']
})
export class PostTweetComponent implements OnInit {
  public focused: boolean;
  constructor() {
    this.focused = false;
  }

  ngOnInit() {
  }

}
