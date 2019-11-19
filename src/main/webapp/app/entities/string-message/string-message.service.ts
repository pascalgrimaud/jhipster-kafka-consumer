import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IStringMessage } from '@/shared/model/string-message.model';

const baseApiUrl = 'api/string-messages';

export default class StringMessageService {
  public find(id: number): Promise<IStringMessage> {
    return new Promise<IStringMessage>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }
}
