import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipologiaPermesso } from 'app/shared/model/tipologia-permesso.model';

@Component({
  selector: 'jhi-tipologia-permesso-detail',
  templateUrl: './tipologia-permesso-detail.component.html',
})
export class TipologiaPermessoDetailComponent implements OnInit {
  tipologiaPermesso: ITipologiaPermesso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipologiaPermesso }) => (this.tipologiaPermesso = tipologiaPermesso));
  }

  previousState(): void {
    window.history.back();
  }
}
