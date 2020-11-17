import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Myztl7SharedModule } from 'app/shared/shared.module';
import { MotivazioneComponent } from './motivazione.component';
import { MotivazioneDetailComponent } from './motivazione-detail.component';
import { MotivazioneUpdateComponent } from './motivazione-update.component';
import { MotivazioneDeleteDialogComponent } from './motivazione-delete-dialog.component';
import { motivazioneRoute } from './motivazione.route';

@NgModule({
  imports: [Myztl7SharedModule, RouterModule.forChild(motivazioneRoute)],
  declarations: [MotivazioneComponent, MotivazioneDetailComponent, MotivazioneUpdateComponent, MotivazioneDeleteDialogComponent],
  entryComponents: [MotivazioneDeleteDialogComponent],
})
export class Myztl7MotivazioneModule {}
