import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAutorizzazione, Autorizzazione } from 'app/shared/model/autorizzazione.model';
import { AutorizzazioneService } from './autorizzazione.service';

@Component({
  selector: 'jhi-autorizzazione-update',
  templateUrl: './autorizzazione-update.component.html',
})
export class AutorizzazioneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descrizione: [],
    stato: [null, [Validators.required]],
  });

  constructor(protected autorizzazioneService: AutorizzazioneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autorizzazione }) => {
      this.updateForm(autorizzazione);
    });
  }

  updateForm(autorizzazione: IAutorizzazione): void {
    this.editForm.patchValue({
      id: autorizzazione.id,
      nome: autorizzazione.nome,
      descrizione: autorizzazione.descrizione,
      stato: autorizzazione.stato,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autorizzazione = this.createFromForm();
    if (autorizzazione.id !== undefined) {
      this.subscribeToSaveResponse(this.autorizzazioneService.update(autorizzazione));
    } else {
      this.subscribeToSaveResponse(this.autorizzazioneService.create(autorizzazione));
    }
  }

  private createFromForm(): IAutorizzazione {
    return {
      ...new Autorizzazione(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
      stato: this.editForm.get(['stato'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutorizzazione>>): void {
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
