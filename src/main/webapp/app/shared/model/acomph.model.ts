import { type IPostoMedicao } from '@/shared/model/posto-medicao.model';

export interface IAcomph {
  id?: number;
  date?: Date | null;
  vazaoDefluenteLido?: number | null;
  vazaoDefluenteConsolidado?: number | null;
  vazaoAfluenteLido?: number | null;
  vazaoAfluenteConsolidado?: number | null;
  vazaoIncrementalConsolidado?: number | null;
  vazaoNaturalConsolidado?: number | null;
  nivelReservatorioLido?: number | null;
  nivelReservatorioConsolidado?: number | null;
  dataPublicacao?: Date | null;
  numPostoMedicao?: IPostoMedicao | null;
}

export class Acomph implements IAcomph {
  constructor(
    public id?: number,
    public date?: Date | null,
    public vazaoDefluenteLido?: number | null,
    public vazaoDefluenteConsolidado?: number | null,
    public vazaoAfluenteLido?: number | null,
    public vazaoAfluenteConsolidado?: number | null,
    public vazaoIncrementalConsolidado?: number | null,
    public vazaoNaturalConsolidado?: number | null,
    public nivelReservatorioLido?: number | null,
    public nivelReservatorioConsolidado?: number | null,
    public dataPublicacao?: Date | null,
    public numPostoMedicao?: IPostoMedicao | null,
  ) {}
}
