import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFestivita, Festivita } from 'app/shared/model/festivita.model';
import { FestivitaService } from './festivita.service';

@Component({
  selector: 'jhi-festivita-update',
  templateUrl: './festivita-update.component.html',
})
export class FestivitaUpdateComponent implements OnInit {
  isSaving = false;
  nomeDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
  });

  constructor(protected festivitaService: FestivitaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivita }) => {
      this.updateForm(festivita);
    });
  }

  updateForm(festivita: IFestivita): void {
    this.editForm.patchValue({
      id: festivita.id,
      nome: festivita.nome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const festivita = this.createFromForm();
    if (festivita.id !== undefined) {
      this.subscribeToSaveResponse(this.festivitaService.update(festivita));
    } else {
      this.subscribeToSaveResponse(this.festivitaService.create(festivita));
    }
  }

  private createFromForm(): IFestivita {
    return {
      ...new Festivita(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFestivita>>): void {
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
