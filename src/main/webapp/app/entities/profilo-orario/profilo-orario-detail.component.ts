import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfiloOrario } from 'app/shared/model/profilo-orario.model';

@Component({
  selector: 'jhi-profilo-orario-detail',
  templateUrl: './profilo-orario-detail.component.html',
})
export class ProfiloOrarioDetailComponent implements OnInit {
  profiloOrario: IProfiloOrario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profiloOrario }) => (this.profiloOrario = profiloOrario));
  }

  previousState(): void {
    window.history.back();
  }
}
