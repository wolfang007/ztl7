import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Myztl7TestModule } from '../../../test.module';
import { TestXComponent } from 'app/entities/test-x/test-x.component';
import { TestXService } from 'app/entities/test-x/test-x.service';
import { TestX } from 'app/shared/model/test-x.model';

describe('Component Tests', () => {
  describe('TestX Management Component', () => {
    let comp: TestXComponent;
    let fixture: ComponentFixture<TestXComponent>;
    let service: TestXService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Myztl7TestModule],
        declarations: [TestXComponent],
      })
        .overrideTemplate(TestXComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestXComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestXService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TestX(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.testXES && comp.testXES[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
