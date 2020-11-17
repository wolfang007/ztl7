import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPermessoTemporaneo, PermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';
import { PermessoTemporaneoService } from './permesso-temporaneo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICalendarizzazione } from 'app/shared/model/calendarizzazione.model';
import { CalendarizzazioneService } from 'app/entities/calendarizzazione/calendarizzazione.service';
import { ITipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';
import { TipologiaPermessoService } from 'app/entities/tipologia-permesso/tipologia-permesso.service';
import { IDurataCosto } from 'app/shared/model/durata-costo.model';
import { DurataCostoService } from 'app/entities/durata-costo/durata-costo.service';
import { IZona } from 'app/shared/model/zona.model';
import { ZonaService } from 'app/entities/zona/zona.service';
import { IMotivazione } from 'app/shared/model/motivazione.model';
import { MotivazioneService } from 'app/entities/motivazione/motivazione.service';
import { IAutorizzazione } from 'app/shared/model/autorizzazione.model';
import { AutorizzazioneService } from 'app/entities/autorizzazione/autorizzazione.service';

type SelectableEntity = ICalendarizzazione | ITipologiaPermesso | IDurataCosto | IZona | IMotivazione | IAutorizzazione;

@Component({
  selector: 'jhi-permesso-temporaneo-update',
  templateUrl: './permesso-temporaneo-update.component.html',
})
export class PermessoTemporaneoUpdateComponent implements OnInit {
  isSaving = false;
  calendarios: ICalendarizzazione[] = [];
  tipologiapermessos: ITipologiaPermesso[] = [];
  duratacostos: IDurataCosto[] = [];
  zonas: IZona[] = [];
  motivaziones: IMotivazione[] = [];
  autorizzaziones: IAutorizzazione[] = [];
  dataInizioDp: any;

  editForm = this.fb.group({
    id: [],
    targa: [null, [Validators.required, Validators.maxLength(10)]],
    domicilioDigitale: [null, [Validators.required, Validators.maxLength(50)]],
    tipoPersona: [],
    nomeRichiedente: [],
    cognomeRichiedente: [],
    ragioneSociale: [],
    codiceFiscaleRichiedente: [],
    partitaIva: [],
    dataInizio: [],
    impostaDiBollo: [],
    costo: [],
    copiaFirmata: [],
    copiaFirmataContentType: [],
    documentoRiconoscimento: [],
    documentoRiconoscimentoContentType: [],
    pagato: [],
    protocollo: [],
    calendario: [],
    tipoPermesso: [],
    durata: [],
    nome: [],
    motivazione: [],
    autorizzazionis: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected permessoTemporaneoService: PermessoTemporaneoService,
    protected calendarizzazioneService: CalendarizzazioneService,
    protected tipologiaPermessoService: TipologiaPermessoService,
    protected durataCostoService: DurataCostoService,
    protected zonaService: ZonaService,
    protected motivazioneService: MotivazioneService,
    protected autorizzazioneService: AutorizzazioneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ permessoTemporaneo }) => {
      this.updateForm(permessoTemporaneo);

      this.calendarizzazioneService
        .query({ 'permessoTemporaneoId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ICalendarizzazione[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICalendarizzazione[]) => {
          if (!permessoTemporaneo.calendario || !permessoTemporaneo.calendario.id) {
            this.calendarios = resBody;
          } else {
            this.calendarizzazioneService
              .find(permessoTemporaneo.calendario.id)
              .pipe(
                map((subRes: HttpResponse<ICalendarizzazione>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICalendarizzazione[]) => (this.calendarios = concatRes));
          }
        });

      this.tipologiaPermessoService
        .query()
        .subscribe((res: HttpResponse<ITipologiaPermesso[]>) => (this.tipologiapermessos = res.body || []));

      this.durataCostoService.query().subscribe((res: HttpResponse<IDurataCosto[]>) => (this.duratacostos = res.body || []));

      this.zonaService.query().subscribe((res: HttpResponse<IZona[]>) => (this.zonas = res.body || []));

      this.motivazioneService.query().subscribe((res: HttpResponse<IMotivazione[]>) => (this.motivaziones = res.body || []));

      this.autorizzazioneService.query().subscribe((res: HttpResponse<IAutorizzazione[]>) => (this.autorizzaziones = res.body || []));
    });
  }

  updateForm(permessoTemporaneo: IPermessoTemporaneo): void {
    this.editForm.patchValue({
      id: permessoTemporaneo.id,
      targa: permessoTemporaneo.targa,
      domicilioDigitale: permessoTemporaneo.domicilioDigitale,
      tipoPersona: permessoTemporaneo.tipoPersona,
      nomeRichiedente: permessoTemporaneo.nomeRichiedente,
      cognomeRichiedente: permessoTemporaneo.cognomeRichiedente,
      ragioneSociale: permessoTemporaneo.ragioneSociale,
      codiceFiscaleRichiedente: permessoTemporaneo.codiceFiscaleRichiedente,
      partitaIva: permessoTemporaneo.partitaIva,
      dataInizio: permessoTemporaneo.dataInizio,
      impostaDiBollo: permessoTemporaneo.impostaDiBollo,
      costo: permessoTemporaneo.costo,
      copiaFirmata: permessoTemporaneo.copiaFirmata,
      copiaFirmataContentType: permessoTemporaneo.copiaFirmataContentType,
      documentoRiconoscimento: permessoTemporaneo.documentoRiconoscimento,
      documentoRiconoscimentoContentType: permessoTemporaneo.documentoRiconoscimentoContentType,
      pagato: permessoTemporaneo.pagato,
      protocollo: permessoTemporaneo.protocollo,
      calendario: permessoTemporaneo.calendario,
      tipoPermesso: permessoTemporaneo.tipoPermesso,
      durata: permessoTemporaneo.durata,
      nome: permessoTemporaneo.nome,
      motivazione: permessoTemporaneo.motivazione,
      autorizzazionis: permessoTemporaneo.autorizzazionis,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('myztl7App.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const permessoTemporaneo = this.createFromForm();
    if (permessoTemporaneo.id !== undefined) {
      this.subscribeToSaveResponse(this.permessoTemporaneoService.update(permessoTemporaneo));
    } else {
      this.subscribeToSaveResponse(this.permessoTemporaneoService.create(permessoTemporaneo));
    }
  }

  private createFromForm(): IPermessoTemporaneo {
    return {
      ...new PermessoTemporaneo(),
      id: this.editForm.get(['id'])!.value,
      targa: this.editForm.get(['targa'])!.value,
      domicilioDigitale: this.editForm.get(['domicilioDigitale'])!.value,
      tipoPersona: this.editForm.get(['tipoPersona'])!.value,
      nomeRichiedente: this.editForm.get(['nomeRichiedente'])!.value,
      cognomeRichiedente: this.editForm.get(['cognomeRichiedente'])!.value,
      ragioneSociale: this.editForm.get(['ragioneSociale'])!.value,
      codiceFiscaleRichiedente: this.editForm.get(['codiceFiscaleRichiedente'])!.value,
      partitaIva: this.editForm.get(['partitaIva'])!.value,
      dataInizio: this.editForm.get(['dataInizio'])!.value,
      impostaDiBollo: this.editForm.get(['impostaDiBollo'])!.value,
      costo: this.editForm.get(['costo'])!.value,
      copiaFirmataContentType: this.editForm.get(['copiaFirmataContentType'])!.value,
      copiaFirmata: this.editForm.get(['copiaFirmata'])!.value,
      documentoRiconoscimentoContentType: this.editForm.get(['documentoRiconoscimentoContentType'])!.value,
      documentoRiconoscimento: this.editForm.get(['documentoRiconoscimento'])!.value,
      pagato: this.editForm.get(['pagato'])!.value,
      protocollo: this.editForm.get(['protocollo'])!.value,
      calendario: this.editForm.get(['calendario'])!.value,
      tipoPermesso: this.editForm.get(['tipoPermesso'])!.value,
      durata: this.editForm.get(['durata'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      motivazione: this.editForm.get(['motivazione'])!.value,
      autorizzazionis: this.editForm.get(['autorizzazionis'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermessoTemporaneo>>): void {
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

  getSelected(selectedVals: IAutorizzazione[], option: IAutorizzazione): IAutorizzazione {
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
