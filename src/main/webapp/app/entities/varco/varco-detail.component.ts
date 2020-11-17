import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVarco } from 'app/shared/model/varco.model';

@Component({
  selector: 'jhi-varco-detail',
  templateUrl: './varco-detail.component.html',
})
export class VarcoDetailComponent implements OnInit {
  varco: IVarco | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ varco }) => (this.varco = varco));
  }

  previousState(): void {
    window.history.back();
  }
}
