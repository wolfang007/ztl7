import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAutorizzazione } from 'app/shared/model/autorizzazione.model';

@Component({
  selector: 'jhi-autorizzazione-detail',
  templateUrl: './autorizzazione-detail.component.html',
})
export class AutorizzazioneDetailComponent implements OnInit {
  autorizzazione: IAutorizzazione | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autorizzazione }) => (this.autorizzazione = autorizzazione));
  }

  previousState(): void {
    window.history.back();
  }
}
