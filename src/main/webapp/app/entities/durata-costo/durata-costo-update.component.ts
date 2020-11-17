import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDurataCosto, DurataCosto } from 'app/shared/model/durata-costo.model';
import { DurataCostoService } from './durata-costo.service';

@Component({
  selector: 'jhi-durata-costo-update',
  templateUrl: './durata-costo-update.component.html',
})
export class DurataCostoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    durata: [null, [Validators.required, Validators.maxLength(50)]],
    descrizione: [],
    costo: [],
  });

  constructor(protected durataCostoService: DurataCostoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ durataCosto }) => {
      this.updateForm(durataCosto);
    });
  }

  updateForm(durataCosto: IDurataCosto): void {
    this.editForm.patchValue({
      id: durataCosto.id,
      durata: durataCosto.durata,
      descrizione: durataCosto.descrizione,
      costo: durataCosto.costo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const durataCosto = this.createFromForm();
    if (durataCosto.id !== undefined) {
      this.subscribeToSaveResponse(this.durataCostoService.update(durataCosto));
    } else {
      this.subscribeToSaveResponse(this.durataCostoService.create(durataCosto));
    }
  }

  private createFromForm(): IDurataCosto {
    return {
      ...new DurataCosto(),
      id: this.editForm.get(['id'])!.value,
      durata: this.editForm.get(['durata'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
      costo: this.editForm.get(['costo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDurataCosto>>): void {
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
