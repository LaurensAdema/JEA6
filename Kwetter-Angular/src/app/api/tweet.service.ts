import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Tweet} from '../domain/tweet';

@Injectable()
export class TweetService {

  constructor(private http: HttpClient) {}

  getAllTweets() {
    return this.http.get<Tweet[]>(`${environment.api_protocol}${environment.api_domain}/tweet`);
  }

  getTweetsForMe() {
    return this.http.get<Tweet[]>(`${environment.api_protocol}${environment.api_domain}/tweets/me`);
  }

  getMyTweets() {
    return this.http.get<Tweet[]>(`${environment.api_protocol}${environment.api_domain}/user/me/tweets`);
  }

  getTweetsOf(id) {
    return this.http.get<Tweet[]>(`${environment.api_protocol}${environment.api_domain}/user/${id}/tweets`);
  }

  postTweet(tweet) {
    return this.http.put<Tweet>(`${environment.api_protocol}${environment.api_domain}/tweet`, tweet);
  }
}
