import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVarco, Varco } from 'app/shared/model/varco.model';
import { VarcoService } from './varco.service';

@Component({
  selector: 'jhi-varco-update',
  templateUrl: './varco-update.component.html',
})
export class VarcoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codice: [null, [Validators.required, Validators.maxLength(50)]],
    descrizionePosizione: [null, [Validators.required, Validators.maxLength(250)]],
    stato: [null, [Validators.required]],
  });

  constructor(protected varcoService: VarcoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ varco }) => {
      this.updateForm(varco);
    });
  }

  updateForm(varco: IVarco): void {
    this.editForm.patchValue({
      id: varco.id,
      codice: varco.codice,
      descrizionePosizione: varco.descrizionePosizione,
      stato: varco.stato,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const varco = this.createFromForm();
    if (varco.id !== undefined) {
      this.subscribeToSaveResponse(this.varcoService.update(varco));
    } else {
      this.subscribeToSaveResponse(this.varcoService.create(varco));
    }
  }

  private createFromForm(): IVarco {
    return {
      ...new Varco(),
      id: this.editForm.get(['id'])!.value,
      codice: this.editForm.get(['codice'])!.value,
      descrizionePosizione: this.editForm.get(['descrizionePosizione'])!.value,
      stato: this.editForm.get(['stato'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVarco>>): void {
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
