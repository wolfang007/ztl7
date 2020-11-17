import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegolaOraria, RegolaOraria } from 'app/shared/model/regola-oraria.model';
import { RegolaOrariaService } from './regola-oraria.service';
import { ITipologiaVeicolo } from 'app/shared/model/tipologia-veicolo.model';
import { TipologiaVeicoloService } from 'app/entities/tipologia-veicolo/tipologia-veicolo.service';

@Component({
  selector: 'jhi-regola-oraria-update',
  templateUrl: './regola-oraria-update.component.html',
})
export class RegolaOrariaUpdateComponent implements OnInit {
  isSaving = false;
  tipologiaveicolos: ITipologiaVeicolo[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    oraInizio: [null, [Validators.required]],
    oraFine: [null, [Validators.required]],
    minutiInizio: [null, [Validators.required]],
    minutiFine: [null, [Validators.required]],
    lunedi: [],
    martedi: [],
    mercoledi: [],
    giovedi: [],
    venerdi: [],
    sabato: [],
    domenica: [],
    festivi: [],
    stato: [null, [Validators.required]],
    tipologiaVeicolos: [],
  });

  constructor(
    protected regolaOrariaService: RegolaOrariaService,
    protected tipologiaVeicoloService: TipologiaVeicoloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regolaOraria }) => {
      this.updateForm(regolaOraria);

      this.tipologiaVeicoloService.query().subscribe((res: HttpResponse<ITipologiaVeicolo[]>) => (this.tipologiaveicolos = res.body || []));
    });
  }

  updateForm(regolaOraria: IRegolaOraria): void {
    this.editForm.patchValue({
      id: regolaOraria.id,
      nome: regolaOraria.nome,
      oraInizio: regolaOraria.oraInizio,
      oraFine: regolaOraria.oraFine,
      minutiInizio: regolaOraria.minutiInizio,
      minutiFine: regolaOraria.minutiFine,
      lunedi: regolaOraria.lunedi,
      martedi: regolaOraria.martedi,
      mercoledi: regolaOraria.mercoledi,
      giovedi: regolaOraria.giovedi,
      venerdi: regolaOraria.venerdi,
      sabato: regolaOraria.sabato,
      domenica: regolaOraria.domenica,
      festivi: regolaOraria.festivi,
      stato: regolaOraria.stato,
      tipologiaVeicolos: regolaOraria.tipologiaVeicolos,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const regolaOraria = this.createFromForm();
    if (regolaOraria.id !== undefined) {
      this.subscribeToSaveResponse(this.regolaOrariaService.update(regolaOraria));
    } else {
      this.subscribeToSaveResponse(this.regolaOrariaService.create(regolaOraria));
    }
  }

  private createFromForm(): IRegolaOraria {
    return {
      ...new RegolaOraria(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      oraInizio: this.editForm.get(['oraInizio'])!.value,
      oraFine: this.editForm.get(['oraFine'])!.value,
      minutiInizio: this.editForm.get(['minutiInizio'])!.value,
      minutiFine: this.editForm.get(['minutiFine'])!.value,
      lunedi: this.editForm.get(['lunedi'])!.value,
      martedi: this.editForm.get(['martedi'])!.value,
      mercoledi: this.editForm.get(['mercoledi'])!.value,
      giovedi: this.editForm.get(['giovedi'])!.value,
      venerdi: this.editForm.get(['venerdi'])!.value,
      sabato: this.editForm.get(['sabato'])!.value,
      domenica: this.editForm.get(['domenica'])!.value,
      festivi: this.editForm.get(['festivi'])!.value,
      stato: this.editForm.get(['stato'])!.value,
      tipologiaVeicolos: this.editForm.get(['tipologiaVeicolos'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegolaOraria>>): void {
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

  trackById(index: number, item: ITipologiaVeicolo): any {
    return item.id;
  }

  getSelected(selectedVals: ITipologiaVeicolo[], option: ITipologiaVeicolo): ITipologiaVeicolo {
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
