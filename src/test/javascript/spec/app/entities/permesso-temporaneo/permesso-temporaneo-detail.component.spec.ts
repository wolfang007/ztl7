import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { Myztl7TestModule } from '../../../test.module';
import { PermessoTemporaneoDetailComponent } from 'app/entities/permesso-temporaneo/permesso-temporaneo-detail.component';
import { PermessoTemporaneo } from 'app/shared/model/permesso-temporaneo.model';

describe('Component Tests', () => {
  describe('PermessoTemporaneo Management Detail Component', () => {
    let comp: PermessoTemporaneoDetailComponent;
    let fixture: ComponentFixture<PermessoTemporaneoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ permessoTemporaneo: new PermessoTemporaneo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [PermessoTemporaneoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PermessoTemporaneoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermessoTemporaneoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load permessoTemporaneo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.permessoTemporaneo).toEqual(jasmine.objectContaining({ id: 123 }));
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
