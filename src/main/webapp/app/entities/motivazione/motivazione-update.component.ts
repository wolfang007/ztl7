import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMotivazione, Motivazione } from 'app/shared/model/motivazione.model';
import { MotivazioneService } from './motivazione.service';

@Component({
  selector: 'jhi-motivazione-update',
  templateUrl: './motivazione-update.component.html',
})
export class MotivazioneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descrizione: [null, [Validators.required, Validators.maxLength(100)]],
  });

  constructor(protected motivazioneService: MotivazioneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ motivazione }) => {
      this.updateForm(motivazione);
    });
  }

  updateForm(motivazione: IMotivazione): void {
    this.editForm.patchValue({
      id: motivazione.id,
      descrizione: motivazione.descrizione,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const motivazione = this.createFromForm();
    if (motivazione.id !== undefined) {
      this.subscribeToSaveResponse(this.motivazioneService.update(motivazione));
    } else {
      this.subscribeToSaveResponse(this.motivazioneService.create(motivazione));
    }
  }

  private createFromForm(): IMotivazione {
    return {
      ...new Motivazione(),
      id: this.editForm.get(['id'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMotivazione>>): void {
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
