export interface IPostoMedicao {
  id?: number;
  nome?: string | null;
  numUsinaHidreletrica?: number | null;
  bacia?: string | null;
  subbacia?: string | null;
  submercado?: string | null;
}

export class PostoMedicao implements IPostoMedicao {
  constructor(
    public id?: number,
    public nome?: string | null,
    public numUsinaHidreletrica?: number | null,
    public bacia?: string | null,
    public subbacia?: string | null,
    public submercado?: string | null,
  ) {}
}
