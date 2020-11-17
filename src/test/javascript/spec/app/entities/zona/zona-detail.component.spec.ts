import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Myztl7TestModule } from '../../../test.module';
import { ZonaDetailComponent } from 'app/entities/zona/zona-detail.component';
import { Zona } from 'app/shared/model/zona.model';

describe('Component Tests', () => {
  describe('Zona Management Detail Component', () => {
    let comp: ZonaDetailComponent;
    let fixture: ComponentFixture<ZonaDetailComponent>;
    const route = ({ data: of({ zona: new Zona(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [ZonaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ZonaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZonaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load zona on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.zona).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
