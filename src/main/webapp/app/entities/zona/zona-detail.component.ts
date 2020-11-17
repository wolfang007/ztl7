import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZona } from 'app/shared/model/zona.model';

@Component({
  selector: 'jhi-zona-detail',
  templateUrl: './zona-detail.component.html',
})
export class ZonaDetailComponent implements OnInit {
  zona: IZona | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zona }) => (this.zona = zona));
  }

  previousState(): void {
    window.history.back();
  }
}
