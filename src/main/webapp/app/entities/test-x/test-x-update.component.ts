import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITestX, TestX } from 'app/shared/model/test-x.model';
import { TestXService } from './test-x.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-test-x-update',
  templateUrl: './test-x-update.component.html',
})
export class TestXUpdateComponent implements OnInit {
  isSaving = false;
  xLocalDateDp: any;

  editForm = this.fb.group({
    id: [],
    xString: [],
    xInteger: [],
    xLong: [],
    xBigDecimal: [],
    xFloat: [],
    xDouble: [],
    xBoolean: [],
    xLocalDate: [],
    xZonedDateTime: [],
    xInstant: [],
    xUUID: [],
    xBlob: [],
    xBlobContentType: [],
    xAnyBlob: [],
    xAnyBlobContentType: [],
    xImageBlob: [],
    xImageBlobContentType: [],
    xTextBlob: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected testXService: TestXService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testX }) => {
      if (!testX.id) {
        const today = moment().startOf('day');
        testX.xZonedDateTime = today;
        testX.xInstant = today;
      }

      this.updateForm(testX);
    });
  }

  updateForm(testX: ITestX): void {
    this.editForm.patchValue({
      id: testX.id,
      xString: testX.xString,
      xInteger: testX.xInteger,
      xLong: testX.xLong,
      xBigDecimal: testX.xBigDecimal,
      xFloat: testX.xFloat,
      xDouble: testX.xDouble,
      xBoolean: testX.xBoolean,
      xLocalDate: testX.xLocalDate,
      xZonedDateTime: testX.xZonedDateTime ? testX.xZonedDateTime.format(DATE_TIME_FORMAT) : null,
      xInstant: testX.xInstant ? testX.xInstant.format(DATE_TIME_FORMAT) : null,
      xUUID: testX.xUUID,
      xBlob: testX.xBlob,
      xBlobContentType: testX.xBlobContentType,
      xAnyBlob: testX.xAnyBlob,
      xAnyBlobContentType: testX.xAnyBlobContentType,
      xImageBlob: testX.xImageBlob,
      xImageBlobContentType: testX.xImageBlobContentType,
      xTextBlob: testX.xTextBlob,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('myztl7App.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const testX = this.createFromForm();
    if (testX.id !== undefined) {
      this.subscribeToSaveResponse(this.testXService.update(testX));
    } else {
      this.subscribeToSaveResponse(this.testXService.create(testX));
    }
  }

  private createFromForm(): ITestX {
    return {
      ...new TestX(),
      id: this.editForm.get(['id'])!.value,
      xString: this.editForm.get(['xString'])!.value,
      xInteger: this.editForm.get(['xInteger'])!.value,
      xLong: this.editForm.get(['xLong'])!.value,
      xBigDecimal: this.editForm.get(['xBigDecimal'])!.value,
      xFloat: this.editForm.get(['xFloat'])!.value,
      xDouble: this.editForm.get(['xDouble'])!.value,
      xBoolean: this.editForm.get(['xBoolean'])!.value,
      xLocalDate: this.editForm.get(['xLocalDate'])!.value,
      xZonedDateTime: this.editForm.get(['xZonedDateTime'])!.value
        ? moment(this.editForm.get(['xZonedDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      xInstant: this.editForm.get(['xInstant'])!.value ? moment(this.editForm.get(['xInstant'])!.value, DATE_TIME_FORMAT) : undefined,
      xUUID: this.editForm.get(['xUUID'])!.value,
      xBlobContentType: this.editForm.get(['xBlobContentType'])!.value,
      xBlob: this.editForm.get(['xBlob'])!.value,
      xAnyBlobContentType: this.editForm.get(['xAnyBlobContentType'])!.value,
      xAnyBlob: this.editForm.get(['xAnyBlob'])!.value,
      xImageBlobContentType: this.editForm.get(['xImageBlobContentType'])!.value,
      xImageBlob: this.editForm.get(['xImageBlob'])!.value,
      xTextBlob: this.editForm.get(['xTextBlob'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestX>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
