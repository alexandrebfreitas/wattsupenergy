import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import AcomphService from './acomph.service';
import { DATE_FORMAT } from '@/shared/composables/date-format';
import { Acomph } from '@/shared/model/acomph.model';

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
  describe('Acomph Service', () => {
    let service: AcomphService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AcomphService();
      currentDate = new Date();
      elemDefault = new Acomph(123, currentDate, 0, 0, 0, 0, 0, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = {
          date: dayjs(currentDate).format(DATE_FORMAT),
          dataPublicacao: dayjs(currentDate).format(DATE_FORMAT),
          ...elemDefault,
        };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Acomph', async () => {
        const returnedFromService = {
          id: 123,
          date: dayjs(currentDate).format(DATE_FORMAT),
          dataPublicacao: dayjs(currentDate).format(DATE_FORMAT),
          ...elemDefault,
        };
        const expected = { date: currentDate, dataPublicacao: currentDate, ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Acomph', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Acomph', async () => {
        const returnedFromService = {
          date: dayjs(currentDate).format(DATE_FORMAT),
          vazaoDefluenteLido: 1,
          vazaoDefluenteConsolidado: 1,
          vazaoAfluenteLido: 1,
          vazaoAfluenteConsolidado: 1,
          vazaoIncrementalConsolidado: 1,
          vazaoNaturalConsolidado: 1,
          nivelReservatorioLido: 1,
          nivelReservatorioConsolidado: 1,
          dataPublicacao: dayjs(currentDate).format(DATE_FORMAT),
          ...elemDefault,
        };

        const expected = { date: currentDate, dataPublicacao: currentDate, ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Acomph', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Acomph', async () => {
        const patchObject = {
          vazaoDefluenteConsolidado: 1,
          vazaoAfluenteLido: 1,
          vazaoAfluenteConsolidado: 1,
          vazaoIncrementalConsolidado: 1,
          vazaoNaturalConsolidado: 1,
          ...new Acomph(),
        };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { date: currentDate, dataPublicacao: currentDate, ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Acomph', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Acomph', async () => {
        const returnedFromService = {
          date: dayjs(currentDate).format(DATE_FORMAT),
          vazaoDefluenteLido: 1,
          vazaoDefluenteConsolidado: 1,
          vazaoAfluenteLido: 1,
          vazaoAfluenteConsolidado: 1,
          vazaoIncrementalConsolidado: 1,
          vazaoNaturalConsolidado: 1,
          nivelReservatorioLido: 1,
          nivelReservatorioConsolidado: 1,
          dataPublicacao: dayjs(currentDate).format(DATE_FORMAT),
          ...elemDefault,
        };
        const expected = { date: currentDate, dataPublicacao: currentDate, ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Acomph', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Acomph', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Acomph', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
