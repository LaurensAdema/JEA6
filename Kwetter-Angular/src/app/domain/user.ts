import {Profile} from './profile';
import {Token} from './token';

export class User {
  email: string;
  password: string;
  id: number;
  profile: Profile;
  following: Array<User>;
  followers: Array<User>;
  tokens: Array<Token>;
}
