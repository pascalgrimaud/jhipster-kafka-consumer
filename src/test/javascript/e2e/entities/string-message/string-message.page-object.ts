import { by, element, ElementArrayFinder, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class StringMessageComponentsPage extends AlertPage {
  title: ElementFinder = element(by.id('string-message-heading'));

  footer: ElementFinder = element(by.id('footer'));

  noRecords: ElementFinder = element(by.css('#page-heading ~ div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#page-heading ~ div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('.btn-info.details'));
  }
}

export class StringMessageDeleteDialog {
  dialog: ElementFinder = element(by.id('removeEntity'));
  title: ElementFinder = element(by.id('consumerApp.stringMessage.delete.question'));
}
