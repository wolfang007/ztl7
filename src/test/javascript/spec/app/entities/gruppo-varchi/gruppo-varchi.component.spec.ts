import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { Myztl7TestModule } from '../../../test.module';
import { GruppoVarchiComponent } from 'app/entities/gruppo-varchi/gruppo-varchi.component';
import { GruppoVarchiService } from 'app/entities/gruppo-varchi/gruppo-varchi.service';
import { GruppoVarchi } from 'app/shared/model/gruppo-varchi.model';

describe('Component Tests', () => {
  describe('GruppoVarchi Management Component', () => {
    let comp: GruppoVarchiComponent;
    let fixture: ComponentFixture<GruppoVarchiComponent>;
    let service: GruppoVarchiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [GruppoVarchiComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(GruppoVarchiComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GruppoVarchiComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GruppoVarchiService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GruppoVarchi(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gruppoVarchis && comp.gruppoVarchis[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GruppoVarchi(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gruppoVarchis && comp.gruppoVarchis[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
