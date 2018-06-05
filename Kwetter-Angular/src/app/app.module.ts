import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { TimelineComponent } from './timeline/timeline.component';
import { PostTweetComponent } from './tweet/post-tweet/post-tweet.component';
import { TweetComponent } from './tweet/tweet.component';
import {HttpClientModule} from '@angular/common/http';
import { MomentModule } from 'angular2-moment';
import { SideProfileComponent } from './side-profile/side-profile.component';
import { LoginComponent } from './header/login/login.component';
import {FormsModule} from '@angular/forms';
import { JwtModule } from '@auth0/angular-jwt';
import {environment} from '../environments/environment';

export function tokenGetter() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    TimelineComponent,
    PostTweetComponent,
    TweetComponent,
    SideProfileComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    MomentModule,
    FormsModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: ['localhost:8080'],
        blacklistedRoutes: [`${environment.api}/auth/`]
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class AppModule { }
