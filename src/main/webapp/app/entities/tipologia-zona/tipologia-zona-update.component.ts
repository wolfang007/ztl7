import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITipologiaZona, TipologiaZona } from 'app/shared/model/tipologia-zona.model';
import { TipologiaZonaService } from './tipologia-zona.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-tipologia-zona-update',
  templateUrl: './tipologia-zona-update.component.html',
})
export class TipologiaZonaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    regoleCircolazione: [null, [Validators.required]],
    stato: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tipologiaZonaService: TipologiaZonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipologiaZona }) => {
      this.updateForm(tipologiaZona);
    });
  }

  updateForm(tipologiaZona: ITipologiaZona): void {
    this.editForm.patchValue({
      id: tipologiaZona.id,
      nome: tipologiaZona.nome,
      regoleCircolazione: tipologiaZona.regoleCircolazione,
      stato: tipologiaZona.stato,
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipologiaZona = this.createFromForm();
    if (tipologiaZona.id !== undefined) {
      this.subscribeToSaveResponse(this.tipologiaZonaService.update(tipologiaZona));
    } else {
      this.subscribeToSaveResponse(this.tipologiaZonaService.create(tipologiaZona));
    }
  }

  private createFromForm(): ITipologiaZona {
    return {
      ...new TipologiaZona(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      regoleCircolazione: this.editForm.get(['regoleCircolazione'])!.value,
      stato: this.editForm.get(['stato'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipologiaZona>>): void {
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
