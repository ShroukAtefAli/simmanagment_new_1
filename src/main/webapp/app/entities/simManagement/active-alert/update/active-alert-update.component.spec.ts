import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ActiveAlertService } from '../service/active-alert.service';
import { IActiveAlert, ActiveAlert } from '../active-alert.model';
import { IDial } from 'app/entities/simManagement/dial/dial.model';
import { DialService } from 'app/entities/simManagement/dial/service/dial.service';

import { ActiveAlertUpdateComponent } from './active-alert-update.component';

describe('ActiveAlert Management Update Component', () => {
  let comp: ActiveAlertUpdateComponent;
  let fixture: ComponentFixture<ActiveAlertUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let activeAlertService: ActiveAlertService;
  let dialService: DialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ActiveAlertUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ActiveAlertUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ActiveAlertUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    activeAlertService = TestBed.inject(ActiveAlertService);
    dialService = TestBed.inject(DialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Dial query and add missing value', () => {
      const activeAlert: IActiveAlert = { id: 456 };
      const dial: IDial = { id: 89072 };
      activeAlert.dial = dial;

      const dialCollection: IDial[] = [{ id: 52802 }];
      jest.spyOn(dialService, 'query').mockReturnValue(of(new HttpResponse({ body: dialCollection })));
      const additionalDials = [dial];
      const expectedCollection: IDial[] = [...additionalDials, ...dialCollection];
      jest.spyOn(dialService, 'addDialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ activeAlert });
      comp.ngOnInit();

      expect(dialService.query).toHaveBeenCalled();
      expect(dialService.addDialToCollectionIfMissing).toHaveBeenCalledWith(dialCollection, ...additionalDials);
      expect(comp.dialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const activeAlert: IActiveAlert = { id: 456 };
      const dial: IDial = { id: 32090 };
      activeAlert.dial = dial;

      activatedRoute.data = of({ activeAlert });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(activeAlert));
      expect(comp.dialsSharedCollection).toContain(dial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ActiveAlert>>();
      const activeAlert = { id: 123 };
      jest.spyOn(activeAlertService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activeAlert });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: activeAlert }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(activeAlertService.update).toHaveBeenCalledWith(activeAlert);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ActiveAlert>>();
      const activeAlert = new ActiveAlert();
      jest.spyOn(activeAlertService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activeAlert });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: activeAlert }));
      saveSubject.complete();

      // THEN
      expect(activeAlertService.create).toHaveBeenCalledWith(activeAlert);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ActiveAlert>>();
      const activeAlert = { id: 123 };
      jest.spyOn(activeAlertService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ activeAlert });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(activeAlertService.update).toHaveBeenCalledWith(activeAlert);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDialById', () => {
      it('Should return tracked Dial primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDialById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
