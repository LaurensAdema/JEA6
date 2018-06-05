import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Tweet} from '../domain/tweet';

@Injectable()
export class TweetService {

  constructor(private http: HttpClient) {}

  getAllTweets() {
    return this.http.get<Tweet[]>(`${environment.api}/tweet`);
  }

  getTweetsForMe() {
    return this.http.get<Tweet[]>(`${environment.api}/tweets/me`);
  }

  getMyTweets() {
    return this.http.get<Tweet[]>(`${environment.api}/user/me/tweets`);
  }

  getTweetsOf(id) {
    return this.http.get<Tweet[]>(`${environment.api}/user/${id}/tweets`);
  }

  postTweet(tweet) {
    return this.http.put<Tweet>(`${environment.api}/tweet`, tweet);
  }
}
