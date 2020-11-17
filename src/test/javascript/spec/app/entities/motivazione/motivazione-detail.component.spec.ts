import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { MotivazioneDetailComponent } from 'app/entities/motivazione/motivazione-detail.component';
import { Motivazione } from 'app/shared/model/motivazione.model';

describe('Component Tests', () => {
  describe('Motivazione Management Detail Component', () => {
    let comp: MotivazioneDetailComponent;
    let fixture: ComponentFixture<MotivazioneDetailComponent>;
    const route = ({ data: of({ motivazione: new Motivazione(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [MotivazioneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MotivazioneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotivazioneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load motivazione on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.motivazione).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
