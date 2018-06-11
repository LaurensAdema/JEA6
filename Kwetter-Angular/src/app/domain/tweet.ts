import {User} from './user';
import {Flag} from './flag';

export class Tweet {
  date: Date;
  id: number;
  message: string;
  user: User;
  likes: Array<User>;
  flags: Array<Flag>;
}
