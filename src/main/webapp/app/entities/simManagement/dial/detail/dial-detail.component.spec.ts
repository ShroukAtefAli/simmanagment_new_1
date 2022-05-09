import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DialDetailComponent } from './dial-detail.component';

describe('Dial Management Detail Component', () => {
  let comp: DialDetailComponent;
  let fixture: ComponentFixture<DialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dial: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dial on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dial).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
