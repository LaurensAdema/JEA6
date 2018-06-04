import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import {Tweet} from './domain/tweet';

@Injectable()
export class TweetService {

  constructor(private http: HttpClient) {}

  getTweets() {
    return this.http.get<Tweet[]>(`${environment.api}/tweet`);
  }
}
