import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserlistComponent} from './userlist/userlist.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'user', component: UserlistComponent},
  {path: 'user/:query', component: ProfileComponent},
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
