import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDurataCosto } from 'app/shared/model/durata-costo.model';

@Component({
  selector: 'jhi-durata-costo-detail',
  templateUrl: './durata-costo-detail.component.html',
})
export class DurataCostoDetailComponent implements OnInit {
  durataCosto: IDurataCosto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ durataCosto }) => (this.durataCosto = durataCosto));
  }

  previousState(): void {
    window.history.back();
  }
}
