import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { Myztl7TestModule } from '../../../test.module';
import { TipologiaZonaDetailComponent } from 'app/entities/tipologia-zona/tipologia-zona-detail.component';
import { TipologiaZona } from 'app/shared/model/tipologia-zona.model';

describe('Component Tests', () => {
  describe('TipologiaZona Management Detail Component', () => {
    let comp: TipologiaZonaDetailComponent;
    let fixture: ComponentFixture<TipologiaZonaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tipologiaZona: new TipologiaZona(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TipologiaZonaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipologiaZonaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipologiaZonaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tipologiaZona on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipologiaZona).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
