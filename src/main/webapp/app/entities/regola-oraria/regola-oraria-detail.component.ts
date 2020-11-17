import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegolaOraria } from 'app/shared/model/regola-oraria.model';

@Component({
  selector: 'jhi-regola-oraria-detail',
  templateUrl: './regola-oraria-detail.component.html',
})
export class RegolaOrariaDetailComponent implements OnInit {
  regolaOraria: IRegolaOraria | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regolaOraria }) => (this.regolaOraria = regolaOraria));
  }

  previousState(): void {
    window.history.back();
  }
}
