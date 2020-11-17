import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGruppoVarchi, GruppoVarchi } from 'app/shared/model/gruppo-varchi.model';
import { GruppoVarchiService } from './gruppo-varchi.service';
import { IVarco } from 'app/shared/model/varco.model';
import { VarcoService } from 'app/entities/varco/varco.service';

@Component({
  selector: 'jhi-gruppo-varchi-update',
  templateUrl: './gruppo-varchi-update.component.html',
})
export class GruppoVarchiUpdateComponent implements OnInit {
  isSaving = false;
  varcos: IVarco[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    descrizione: [null, [Validators.maxLength(250)]],
    descrioneIntervalli: [],
    stato: [null, [Validators.required]],
    posiziones: [],
  });

  constructor(
    protected gruppoVarchiService: GruppoVarchiService,
    protected varcoService: VarcoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gruppoVarchi }) => {
      this.updateForm(gruppoVarchi);

      this.varcoService.query().subscribe((res: HttpResponse<IVarco[]>) => (this.varcos = res.body || []));
    });
  }

  updateForm(gruppoVarchi: IGruppoVarchi): void {
    this.editForm.patchValue({
      id: gruppoVarchi.id,
      nome: gruppoVarchi.nome,
      descrizione: gruppoVarchi.descrizione,
      descrioneIntervalli: gruppoVarchi.descrioneIntervalli,
      stato: gruppoVarchi.stato,
      posiziones: gruppoVarchi.posiziones,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gruppoVarchi = this.createFromForm();
    if (gruppoVarchi.id !== undefined) {
      this.subscribeToSaveResponse(this.gruppoVarchiService.update(gruppoVarchi));
    } else {
      this.subscribeToSaveResponse(this.gruppoVarchiService.create(gruppoVarchi));
    }
  }

  private createFromForm(): IGruppoVarchi {
    return {
      ...new GruppoVarchi(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
      descrioneIntervalli: this.editForm.get(['descrioneIntervalli'])!.value,
      stato: this.editForm.get(['stato'])!.value,
      posiziones: this.editForm.get(['posiziones'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGruppoVarchi>>): void {
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

  trackById(index: number, item: IVarco): any {
    return item.id;
  }

  getSelected(selectedVals: IVarco[], option: IVarco): IVarco {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
