import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMotivazione } from 'app/shared/model/motivazione.model';

@Component({
  selector: 'jhi-motivazione-detail',
  templateUrl: './motivazione-detail.component.html',
})
export class MotivazioneDetailComponent implements OnInit {
  motivazione: IMotivazione | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ motivazione }) => (this.motivazione = motivazione));
  }

  previousState(): void {
    window.history.back();
  }
}
