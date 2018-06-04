import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { environment } from '../../environments/environment';
import {Tweet} from '../domain/tweet';
import {Observable} from 'rxjs';

@Injectable()
export class TweetService {

  constructor(private http: HttpClient) {}

  getTweets() {
    return this.http.get<Tweet[]>(`${environment.api}/tweet`);
  }

  postTweet(tweet) {
    return this.http.put<Tweet>(`${environment.api}/tweet`, tweet);
  }
}
