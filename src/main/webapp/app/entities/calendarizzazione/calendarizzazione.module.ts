import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Myztl7SharedModule } from 'app/shared/shared.module';
import { CalendarizzazioneComponent } from './calendarizzazione.component';
import { CalendarizzazioneDetailComponent } from './calendarizzazione-detail.component';
import { CalendarizzazioneUpdateComponent } from './calendarizzazione-update.component';
import { CalendarizzazioneDeleteDialogComponent } from './calendarizzazione-delete-dialog.component';
import { calendarizzazioneRoute } from './calendarizzazione.route';

@NgModule({
  imports: [Myztl7SharedModule, RouterModule.forChild(calendarizzazioneRoute)],
  declarations: [
    CalendarizzazioneComponent,
    CalendarizzazioneDetailComponent,
    CalendarizzazioneUpdateComponent,
    CalendarizzazioneDeleteDialogComponent,
  ],
  entryComponents: [CalendarizzazioneDeleteDialogComponent],
})
export class Myztl7CalendarizzazioneModule {}
