import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Myztl7SharedModule } from 'app/shared/shared.module';
import { AutorizzazioneComponent } from './autorizzazione.component';
import { AutorizzazioneDetailComponent } from './autorizzazione-detail.component';
import { AutorizzazioneUpdateComponent } from './autorizzazione-update.component';
import { AutorizzazioneDeleteDialogComponent } from './autorizzazione-delete-dialog.component';
import { autorizzazioneRoute } from './autorizzazione.route';

@NgModule({
  imports: [Myztl7SharedModule, RouterModule.forChild(autorizzazioneRoute)],
  declarations: [
    AutorizzazioneComponent,
    AutorizzazioneDetailComponent,
    AutorizzazioneUpdateComponent,
    AutorizzazioneDeleteDialogComponent,
  ],
  entryComponents: [AutorizzazioneDeleteDialogComponent],
})
export class Myztl7AutorizzazioneModule {}
