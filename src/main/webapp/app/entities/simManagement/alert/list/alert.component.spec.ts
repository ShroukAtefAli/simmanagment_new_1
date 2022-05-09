import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AlertService } from '../service/alert.service';

import { AlertComponent } from './alert.component';

describe('Alert Management Component', () => {
  let comp: AlertComponent;
  let fixture: ComponentFixture<AlertComponent>;
  let service: AlertService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AlertComponent],
    })
      .overrideTemplate(AlertComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AlertComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AlertService);

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
    expect(comp.alerts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
