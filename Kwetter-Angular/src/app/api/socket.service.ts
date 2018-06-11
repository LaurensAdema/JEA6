import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {Subject} from 'rxjs';
import {Tweet} from '../domain/tweet';

@Injectable()
export class SocketService {

  private tweetUpdated = new Subject<Tweet>();
  tweetUpdated$ = this.tweetUpdated.asObservable();

  private socket: WebSocket;

  startListener(page?: string) {
    if (this.socket) {
      if (this.socket.readyState !== WebSocket.OPEN && this.socket.readyState !== WebSocket.CONNECTING) {
        this.socket.close();
      } else {
        this.changePage(page);
        return;
      }
    }

    this.socket = new WebSocket(this.getSocketURL(page));
    this.socket.onmessage = e => this.onMessage(e);
  }

  restartListener(page?: string) {
    if (this.socket) {
      this.socket.close();
    }

    this.startListener(page);
  }

  onMessage(e) {
    this.tweetUpdated.next(JSON.parse(e.data));
  }

  changePage(page?: string) {
    if (this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(page);
    }
  }

  private getSocketURL(page?: string): string {
    let url = `${environment.ws_protocol}${environment.api_domain}/events/`;

    if (page) {
      url += page;
    }

    const token = localStorage.getItem('access_token');
    if (token) {
      url += `?access_token=${token}`;
    }

    return url;
  }
}
