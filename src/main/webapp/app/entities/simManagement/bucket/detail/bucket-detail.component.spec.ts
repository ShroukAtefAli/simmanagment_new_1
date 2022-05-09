import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BucketDetailComponent } from './bucket-detail.component';

describe('Bucket Management Detail Component', () => {
  let comp: BucketDetailComponent;
  let fixture: ComponentFixture<BucketDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BucketDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bucket: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BucketDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BucketDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bucket on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bucket).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
