import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';

@Injectable()
export class SocketService {

  private socket: WebSocket;

  startListener(page?: string) {
    if (this.socket) {
      this.socket.close();
    }
    let url = `ws://${environment.api_domain}/events/`;

    if (page) {
      url += page;
    }

    const token = localStorage.getItem('access_token');
    if (token) {
      url += `?access_token=${token}`;
    }

    this.socket = new WebSocket(url);
    this.socket.onmessage = e => {
      console.log(e);
    };
  }

  changePage(page?: string) {
    this.socket.send(page);
  }
}
