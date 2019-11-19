import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IJsonMessage } from '@/shared/model/json-message.model';

const baseApiUrl = 'api/json-messages';

export default class JsonMessageService {
  public find(id: number): Promise<IJsonMessage> {
    return new Promise<IJsonMessage>(resolve => {
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
