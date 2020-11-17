import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProfiloOrario, ProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { ProfiloOrarioService } from './profilo-orario.service';
import { IRegolaOraria } from 'app/shared/model/regola-oraria.model';
import { RegolaOrariaService } from 'app/entities/regola-oraria/regola-oraria.service';

@Component({
  selector: 'jhi-profilo-orario-update',
  templateUrl: './profilo-orario-update.component.html',
})
export class ProfiloOrarioUpdateComponent implements OnInit {
  isSaving = false;
  regolaorarias: IRegolaOraria[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    stato: [null, [Validators.required]],
    regolaOrarias: [],
  });

  constructor(
    protected profiloOrarioService: ProfiloOrarioService,
    protected regolaOrariaService: RegolaOrariaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profiloOrario }) => {
      this.updateForm(profiloOrario);

      this.regolaOrariaService.query().subscribe((res: HttpResponse<IRegolaOraria[]>) => (this.regolaorarias = res.body || []));
    });
  }

  updateForm(profiloOrario: IProfiloOrario): void {
    this.editForm.patchValue({
      id: profiloOrario.id,
      nome: profiloOrario.nome,
      stato: profiloOrario.stato,
      regolaOrarias: profiloOrario.regolaOrarias,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profiloOrario = this.createFromForm();
    if (profiloOrario.id !== undefined) {
      this.subscribeToSaveResponse(this.profiloOrarioService.update(profiloOrario));
    } else {
      this.subscribeToSaveResponse(this.profiloOrarioService.create(profiloOrario));
    }
  }

  private createFromForm(): IProfiloOrario {
    return {
      ...new ProfiloOrario(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      stato: this.editForm.get(['stato'])!.value,
      regolaOrarias: this.editForm.get(['regolaOrarias'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfiloOrario>>): void {
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

  trackById(index: number, item: IRegolaOraria): any {
    return item.id;
  }

  getSelected(selectedVals: IRegolaOraria[], option: IRegolaOraria): IRegolaOraria {
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
