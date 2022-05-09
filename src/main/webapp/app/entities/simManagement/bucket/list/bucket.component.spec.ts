import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BucketService } from '../service/bucket.service';

import { BucketComponent } from './bucket.component';

describe('Bucket Management Component', () => {
  let comp: BucketComponent;
  let fixture: ComponentFixture<BucketComponent>;
  let service: BucketService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BucketComponent],
    })
      .overrideTemplate(BucketComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BucketComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BucketService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.buckets?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
