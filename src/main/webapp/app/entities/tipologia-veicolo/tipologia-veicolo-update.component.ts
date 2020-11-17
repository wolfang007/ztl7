import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipologiaVeicolo, TipologiaVeicolo } from 'app/shared/model/tipologia-veicolo.model';
import { TipologiaVeicoloService } from './tipologia-veicolo.service';

@Component({
  selector: 'jhi-tipologia-veicolo-update',
  templateUrl: './tipologia-veicolo-update.component.html',
})
export class TipologiaVeicoloUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    stato: [null, [Validators.required]],
  });

  constructor(
    protected tipologiaVeicoloService: TipologiaVeicoloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipologiaVeicolo }) => {
      this.updateForm(tipologiaVeicolo);
    });
  }

  updateForm(tipologiaVeicolo: ITipologiaVeicolo): void {
    this.editForm.patchValue({
      id: tipologiaVeicolo.id,
      nome: tipologiaVeicolo.nome,
      stato: tipologiaVeicolo.stato,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipologiaVeicolo = this.createFromForm();
    if (tipologiaVeicolo.id !== undefined) {
      this.subscribeToSaveResponse(this.tipologiaVeicoloService.update(tipologiaVeicolo));
    } else {
      this.subscribeToSaveResponse(this.tipologiaVeicoloService.create(tipologiaVeicolo));
    }
  }

  private createFromForm(): ITipologiaVeicolo {
    return {
      ...new TipologiaVeicolo(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      stato: this.editForm.get(['stato'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipologiaVeicolo>>): void {
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
