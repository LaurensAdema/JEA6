import {Profile} from './profile';

export class User {
  email: string;
  password: string;
  id: number;
  profile: Profile;
  following: Array<User>;
  followers: Array<User>;
}
