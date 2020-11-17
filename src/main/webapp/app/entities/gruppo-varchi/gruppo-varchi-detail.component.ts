import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGruppoVarchi } from 'app/shared/model/gruppo-varchi.model';

@Component({
  selector: 'jhi-gruppo-varchi-detail',
  templateUrl: './gruppo-varchi-detail.component.html',
})
export class GruppoVarchiDetailComponent implements OnInit {
  gruppoVarchi: IGruppoVarchi | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gruppoVarchi }) => (this.gruppoVarchi = gruppoVarchi));
  }

  previousState(): void {
    window.history.back();
  }
}
