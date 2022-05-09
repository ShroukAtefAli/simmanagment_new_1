import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActiveAlertDetailComponent } from './active-alert-detail.component';

describe('ActiveAlert Management Detail Component', () => {
  let comp: ActiveAlertDetailComponent;
  let fixture: ComponentFixture<ActiveAlertDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActiveAlertDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ activeAlert: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ActiveAlertDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ActiveAlertDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load activeAlert on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.activeAlert).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
