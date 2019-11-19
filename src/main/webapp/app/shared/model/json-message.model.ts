export interface IJsonMessage {
  id?: number;
  field1?: string;
  field2?: string;
  number?: number;
  receivedAt?: Date;
}

export class JsonMessage implements IJsonMessage {
  constructor(public id?: number, public field1?: string, public field2?: string, public number?: number, public receivedAt?: Date) {}
}
