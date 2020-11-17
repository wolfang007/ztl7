import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITestX } from 'app/shared/model/test-x.model';
import { TestXService } from './test-x.service';
import { TestXDeleteDialogComponent } from './test-x-delete-dialog.component';

@Component({
  selector: 'jhi-test-x',
  templateUrl: './test-x.component.html',
})
export class TestXComponent implements OnInit, OnDestroy {
  testXES?: ITestX[];
  eventSubscriber?: Subscription;

  constructor(
    protected testXService: TestXService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.testXService.query().subscribe((res: HttpResponse<ITestX[]>) => (this.testXES = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTestXES();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITestX): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInTestXES(): void {
    this.eventSubscriber = this.eventManager.subscribe('testXListModification', () => this.loadAll());
  }

  delete(testX: ITestX): void {
    const modalRef = this.modalService.open(TestXDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.testX = testX;
  }
}
