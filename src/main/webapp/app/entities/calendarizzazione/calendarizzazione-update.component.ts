import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICalendarizzazione, Calendarizzazione } from 'app/shared/model/calendarizzazione.model';
import { CalendarizzazioneService } from './calendarizzazione.service';

@Component({
  selector: 'jhi-calendarizzazione-update',
  templateUrl: './calendarizzazione-update.component.html',
})
export class CalendarizzazioneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    lunedi: [],
    martedi: [],
    mercoledi: [],
    giovedi: [],
    venerdi: [],
    sabato: [],
    domenica: [],
    siRipete: [],
    festivi: [],
  });

  constructor(
    protected calendarizzazioneService: CalendarizzazioneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendarizzazione }) => {
      this.updateForm(calendarizzazione);
    });
  }

  updateForm(calendarizzazione: ICalendarizzazione): void {
    this.editForm.patchValue({
      id: calendarizzazione.id,
      lunedi: calendarizzazione.lunedi,
      martedi: calendarizzazione.martedi,
      mercoledi: calendarizzazione.mercoledi,
      giovedi: calendarizzazione.giovedi,
      venerdi: calendarizzazione.venerdi,
      sabato: calendarizzazione.sabato,
      domenica: calendarizzazione.domenica,
      siRipete: calendarizzazione.siRipete,
      festivi: calendarizzazione.festivi,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calendarizzazione = this.createFromForm();
    if (calendarizzazione.id !== undefined) {
      this.subscribeToSaveResponse(this.calendarizzazioneService.update(calendarizzazione));
    } else {
      this.subscribeToSaveResponse(this.calendarizzazioneService.create(calendarizzazione));
    }
  }

  private createFromForm(): ICalendarizzazione {
    return {
      ...new Calendarizzazione(),
      id: this.editForm.get(['id'])!.value,
      lunedi: this.editForm.get(['lunedi'])!.value,
      martedi: this.editForm.get(['martedi'])!.value,
      mercoledi: this.editForm.get(['mercoledi'])!.value,
      giovedi: this.editForm.get(['giovedi'])!.value,
      venerdi: this.editForm.get(['venerdi'])!.value,
      sabato: this.editForm.get(['sabato'])!.value,
      domenica: this.editForm.get(['domenica'])!.value,
      siRipete: this.editForm.get(['siRipete'])!.value,
      festivi: this.editForm.get(['festivi'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalendarizzazione>>): void {
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
