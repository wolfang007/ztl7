import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipologiaPermesso, TipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';
import { TipologiaPermessoService } from './tipologia-permesso.service';

@Component({
  selector: 'jhi-tipologia-permesso-update',
  templateUrl: './tipologia-permesso-update.component.html',
})
export class TipologiaPermessoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    codice: [null, [Validators.required]],
  });

  constructor(
    protected tipologiaPermessoService: TipologiaPermessoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipologiaPermesso }) => {
      this.updateForm(tipologiaPermesso);
    });
  }

  updateForm(tipologiaPermesso: ITipologiaPermesso): void {
    this.editForm.patchValue({
      id: tipologiaPermesso.id,
      nome: tipologiaPermesso.nome,
      codice: tipologiaPermesso.codice,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipologiaPermesso = this.createFromForm();
    if (tipologiaPermesso.id !== undefined) {
      this.subscribeToSaveResponse(this.tipologiaPermessoService.update(tipologiaPermesso));
    } else {
      this.subscribeToSaveResponse(this.tipologiaPermessoService.create(tipologiaPermesso));
    }
  }

  private createFromForm(): ITipologiaPermesso {
    return {
      ...new TipologiaPermesso(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      codice: this.editForm.get(['codice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipologiaPermesso>>): void {
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
