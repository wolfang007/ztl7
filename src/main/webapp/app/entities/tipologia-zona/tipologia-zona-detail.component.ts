import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITipologiaZona } from 'app/shared/model/tipologia-zona.model';

@Component({
  selector: 'jhi-tipologia-zona-detail',
  templateUrl: './tipologia-zona-detail.component.html',
})
export class TipologiaZonaDetailComponent implements OnInit {
  tipologiaZona: ITipologiaZona | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipologiaZona }) => (this.tipologiaZona = tipologiaZona));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
