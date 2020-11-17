import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tipologia-zona',
        loadChildren: () => import('./tipologia-zona/tipologia-zona.module').then(m => m.Myztl7TipologiaZonaModule),
      },
      {
        path: 'varco',
        loadChildren: () => import('./varco/varco.module').then(m => m.Myztl7VarcoModule),
      },
      {
        path: 'gruppo-varchi',
        loadChildren: () => import('./gruppo-varchi/gruppo-varchi.module').then(m => m.Myztl7GruppoVarchiModule),
      },
      {
        path: 'tipologia-veicolo',
        loadChildren: () => import('./tipologia-veicolo/tipologia-veicolo.module').then(m => m.Myztl7TipologiaVeicoloModule),
      },
      {
        path: 'festivita',
        loadChildren: () => import('./festivita/festivita.module').then(m => m.Myztl7FestivitaModule),
      },
      {
        path: 'regola-oraria',
        loadChildren: () => import('./regola-oraria/regola-oraria.module').then(m => m.Myztl7RegolaOrariaModule),
      },
      {
        path: 'profilo-orario',
        loadChildren: () => import('./profilo-orario/profilo-orario.module').then(m => m.Myztl7ProfiloOrarioModule),
      },
      {
        path: 'autorizzazione',
        loadChildren: () => import('./autorizzazione/autorizzazione.module').then(m => m.Myztl7AutorizzazioneModule),
      },
      {
        path: 'zona',
        loadChildren: () => import('./zona/zona.module').then(m => m.Myztl7ZonaModule),
      },
      {
        path: 'tipologia-permesso',
        loadChildren: () => import('./tipologia-permesso/tipologia-permesso.module').then(m => m.Myztl7TipologiaPermessoModule),
      },
      {
        path: 'durata-costo',
        loadChildren: () => import('./durata-costo/durata-costo.module').then(m => m.Myztl7DurataCostoModule),
      },
      {
        path: 'motivazione',
        loadChildren: () => import('./motivazione/motivazione.module').then(m => m.Myztl7MotivazioneModule),
      },
      {
        path: 'calendarizzazione',
        loadChildren: () => import('./calendarizzazione/calendarizzazione.module').then(m => m.Myztl7CalendarizzazioneModule),
      },
      {
        path: 'test-x',
        loadChildren: () => import('./test-x/test-x.module').then(m => m.Myztl7TestXModule),
      },
      {
        path: 'permesso-temporaneo',
        loadChildren: () => import('./permesso-temporaneo/permesso-temporaneo.module').then(m => m.Myztl7PermessoTemporaneoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class Myztl7EntityModule {}
