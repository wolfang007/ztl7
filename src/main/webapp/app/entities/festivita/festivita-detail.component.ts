import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFestivita } from 'app/shared/model/festivita.model';

@Component({
  selector: 'jhi-festivita-detail',
  templateUrl: './festivita-detail.component.html',
})
export class FestivitaDetailComponent implements OnInit {
  festivita: IFestivita | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ festivita }) => (this.festivita = festivita));
  }

  previousState(): void {
    window.history.back();
  }
}
