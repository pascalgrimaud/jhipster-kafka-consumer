export interface IStringMessage {
  id?: number;
  message?: string;
  receivedAt?: Date;
}

export class StringMessage implements IStringMessage {
  constructor(public id?: number, public message?: string, public receivedAt?: Date) {}
}
