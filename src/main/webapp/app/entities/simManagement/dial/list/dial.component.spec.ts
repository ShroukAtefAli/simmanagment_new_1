import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DialService } from '../service/dial.service';

import { DialComponent } from './dial.component';

describe('Dial Management Component', () => {
  let comp: DialComponent;
  let fixture: ComponentFixture<DialComponent>;
  let service: DialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DialComponent],
    })
      .overrideTemplate(DialComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DialComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DialService);

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
    expect(comp.dials?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
