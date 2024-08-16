/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import ConvocatoriaService from '@/entities/convocatoria/convocatoria.service';
import { Convocatoria } from '@/shared/model/convocatoria.model';

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
  describe('Convocatoria Service', () => {
    let service: ConvocatoriaService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ConvocatoriaService();
      currentDate = new Date();
      elemDefault = new Convocatoria('ABC', 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaCreacion: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a Convocatoria', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
            fechaCreacion: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Convocatoria', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Convocatoria', async () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            anio: 1,
            fechaCreacion: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Convocatoria', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Convocatoria', async () => {
        const patchObject = Object.assign(
          {
            fechaCreacion: dayjs(currentDate).format(DATE_FORMAT),
          },
          new Convocatoria()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Convocatoria', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Convocatoria', async () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            anio: 1,
            fechaCreacion: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Convocatoria', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Convocatoria', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Convocatoria', async () => {
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
