create table cotacao_diaria (
  id identity not null primary key,
  acao varchar (20) not null,
  data DATE not null,
  valor double not null
);