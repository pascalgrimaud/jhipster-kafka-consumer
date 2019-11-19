import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStringMessage } from '@/shared/model/string-message.model';
import StringMessageService from './string-message.service';

@Component
export default class StringMessageDetails extends Vue {
  @Inject('stringMessageService') private stringMessageService: () => StringMessageService;
  public stringMessage: IStringMessage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stringMessageId) {
        vm.retrieveStringMessage(to.params.stringMessageId);
      }
    });
  }

  public retrieveStringMessage(stringMessageId) {
    this.stringMessageService()
      .find(stringMessageId)
      .then(res => {
        this.stringMessage = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
