import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ActiveAlertService } from '../service/active-alert.service';

import { ActiveAlertComponent } from './active-alert.component';

describe('ActiveAlert Management Component', () => {
  let comp: ActiveAlertComponent;
  let fixture: ComponentFixture<ActiveAlertComponent>;
  let service: ActiveAlertService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ActiveAlertComponent],
    })
      .overrideTemplate(ActiveAlertComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ActiveAlertComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ActiveAlertService);

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
    expect(comp.activeAlerts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
