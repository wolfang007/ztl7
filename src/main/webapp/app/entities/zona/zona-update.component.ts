import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IZona, Zona } from 'app/shared/model/zona.model';
import { ZonaService } from './zona.service';
import { IProfiloOrario } from 'app/shared/model/profilo-orario.model';
import { ProfiloOrarioService } from 'app/entities/profilo-orario/profilo-orario.service';
import { ITipologiaZona } from 'app/shared/model/tipologia-zona.model';
import { TipologiaZonaService } from 'app/entities/tipologia-zona/tipologia-zona.service';
import { IGruppoVarchi } from 'app/shared/model/gruppo-varchi.model';
import { GruppoVarchiService } from 'app/entities/gruppo-varchi/gruppo-varchi.service';

type SelectableEntity = IProfiloOrario | ITipologiaZona | IGruppoVarchi;

@Component({
  selector: 'jhi-zona-update',
  templateUrl: './zona-update.component.html',
})
export class ZonaUpdateComponent implements OnInit {
  isSaving = false;
  profiloorarios: IProfiloOrario[] = [];
  tipologiazonas: ITipologiaZona[] = [];
  gruppovarchis: IGruppoVarchi[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    stato: [null, [Validators.required]],
    profiloOrario: [],
    tipologiaZona: [],
    gruppoVarchis: [],
  });

  constructor(
    protected zonaService: ZonaService,
    protected profiloOrarioService: ProfiloOrarioService,
    protected tipologiaZonaService: TipologiaZonaService,
    protected gruppoVarchiService: GruppoVarchiService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zona }) => {
      this.updateForm(zona);

      this.profiloOrarioService.query().subscribe((res: HttpResponse<IProfiloOrario[]>) => (this.profiloorarios = res.body || []));

      this.tipologiaZonaService.query().subscribe((res: HttpResponse<ITipologiaZona[]>) => (this.tipologiazonas = res.body || []));

      this.gruppoVarchiService.query().subscribe((res: HttpResponse<IGruppoVarchi[]>) => (this.gruppovarchis = res.body || []));
    });
  }

  updateForm(zona: IZona): void {
    this.editForm.patchValue({
      id: zona.id,
      nome: zona.nome,
      stato: zona.stato,
      profiloOrario: zona.profiloOrario,
      tipologiaZona: zona.tipologiaZona,
      gruppoVarchis: zona.gruppoVarchis,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zona = this.createFromForm();
    if (zona.id !== undefined) {
      this.subscribeToSaveResponse(this.zonaService.update(zona));
    } else {
      this.subscribeToSaveResponse(this.zonaService.create(zona));
    }
  }

  private createFromForm(): IZona {
    return {
      ...new Zona(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      stato: this.editForm.get(['stato'])!.value,
      profiloOrario: this.editForm.get(['profiloOrario'])!.value,
      tipologiaZona: this.editForm.get(['tipologiaZona'])!.value,
      gruppoVarchis: this.editForm.get(['gruppoVarchis'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZona>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IGruppoVarchi[], option: IGruppoVarchi): IGruppoVarchi {
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
