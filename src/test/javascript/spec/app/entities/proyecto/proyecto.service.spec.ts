/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { Proyecto } from '@/shared/model/proyecto.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Proyecto Service', () => {
    let service: ProyectoService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ProyectoService();
      currentDate = new Date();
      elemDefault = new Proyecto('ABC', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            solicitudFecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Proyecto', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
            solicitudFecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            solicitudFecha: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Proyecto', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Proyecto', async () => {
        const returnedFromService = Object.assign(
          {
            detalle: 'BBBBBB',
            modalidad: 'BBBBBB',
            titulo: 'BBBBBB',
            solicitudClave: 'BBBBBB',
            solicitudFecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
            completo: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            solicitudFecha: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Proyecto', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Proyecto', async () => {
        const patchObject = Object.assign(
          {
            detalle: 'BBBBBB',
            titulo: 'BBBBBB',
          },
          new Proyecto()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            solicitudFecha: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Proyecto', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Proyecto', async () => {
        const returnedFromService = Object.assign(
          {
            detalle: 'BBBBBB',
            modalidad: 'BBBBBB',
            titulo: 'BBBBBB',
            solicitudClave: 'BBBBBB',
            solicitudFecha: dayjs(currentDate).format(DATE_TIME_FORMAT),
            completo: true,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            solicitudFecha: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Proyecto', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Proyecto', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Proyecto', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
