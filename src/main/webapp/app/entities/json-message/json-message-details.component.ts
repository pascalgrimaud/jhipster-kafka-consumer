import { Component, Vue, Inject } from 'vue-property-decorator';

import { IJsonMessage } from '@/shared/model/json-message.model';
import JsonMessageService from './json-message.service';

@Component
export default class JsonMessageDetails extends Vue {
  @Inject('jsonMessageService') private jsonMessageService: () => JsonMessageService;
  public jsonMessage: IJsonMessage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.jsonMessageId) {
        vm.retrieveJsonMessage(to.params.jsonMessageId);
      }
    });
  }

  public retrieveJsonMessage(jsonMessageId) {
    this.jsonMessageService()
      .find(jsonMessageId)
      .then(res => {
        this.jsonMessage = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
