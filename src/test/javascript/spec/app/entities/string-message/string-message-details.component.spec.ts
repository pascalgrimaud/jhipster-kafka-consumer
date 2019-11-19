/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StringMessageDetailComponent from '@/entities/string-message/string-message-details.vue';
import StringMessageClass from '@/entities/string-message/string-message-details.component';
import StringMessageService from '@/entities/string-message/string-message.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('StringMessage Management Detail Component', () => {
    let wrapper: Wrapper<StringMessageClass>;
    let comp: StringMessageClass;
    let stringMessageServiceStub: SinonStubbedInstance<StringMessageService>;

    beforeEach(() => {
      stringMessageServiceStub = sinon.createStubInstance<StringMessageService>(StringMessageService);

      wrapper = shallowMount<StringMessageClass>(StringMessageDetailComponent, {
        store,
        localVue,
        provide: { stringMessageService: () => stringMessageServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStringMessage = { id: 123 };
        stringMessageServiceStub.find.resolves(foundStringMessage);

        // WHEN
        comp.retrieveStringMessage(123);
        await comp.$nextTick();

        // THEN
        expect(comp.stringMessage).toBe(foundStringMessage);
      });
    });
  });
});
