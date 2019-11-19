/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import JsonMessageDetailComponent from '@/entities/json-message/json-message-details.vue';
import JsonMessageClass from '@/entities/json-message/json-message-details.component';
import JsonMessageService from '@/entities/json-message/json-message.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('JsonMessage Management Detail Component', () => {
    let wrapper: Wrapper<JsonMessageClass>;
    let comp: JsonMessageClass;
    let jsonMessageServiceStub: SinonStubbedInstance<JsonMessageService>;

    beforeEach(() => {
      jsonMessageServiceStub = sinon.createStubInstance<JsonMessageService>(JsonMessageService);

      wrapper = shallowMount<JsonMessageClass>(JsonMessageDetailComponent, {
        store,
        localVue,
        provide: { jsonMessageService: () => jsonMessageServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundJsonMessage = { id: 123 };
        jsonMessageServiceStub.find.resolves(foundJsonMessage);

        // WHEN
        comp.retrieveJsonMessage(123);
        await comp.$nextTick();

        // THEN
        expect(comp.jsonMessage).toBe(foundJsonMessage);
      });
    });
  });
});
