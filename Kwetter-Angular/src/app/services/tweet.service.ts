import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable
export class TweetService {
  constructor(private http: HttpClient) {}
}
