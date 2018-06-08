import {Component, OnInit} from '@angular/core';
import {UserService} from './api/user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AuthenticationService} from './api/authentication.service';
import {SocketService} from './api/socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [AuthenticationService, UserService, JwtHelperService, SocketService]
})
export class AppComponent implements OnInit {

  constructor(private socketService: SocketService) { }

  ngOnInit(): void {
    this.socketService.startListener();
  }
}


