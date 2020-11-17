import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Myztl7SharedModule } from 'app/shared/shared.module';
import { DurataCostoComponent } from './durata-costo.component';
import { DurataCostoDetailComponent } from './durata-costo-detail.component';
import { DurataCostoUpdateComponent } from './durata-costo-update.component';
import { DurataCostoDeleteDialogComponent } from './durata-costo-delete-dialog.component';
import { durataCostoRoute } from './durata-costo.route';

@NgModule({
  imports: [Myztl7SharedModule, RouterModule.forChild(durataCostoRoute)],
  declarations: [DurataCostoComponent, DurataCostoDetailComponent, DurataCostoUpdateComponent, DurataCostoDeleteDialogComponent],
  entryComponents: [DurataCostoDeleteDialogComponent],
})
export class Myztl7DurataCostoModule {}
