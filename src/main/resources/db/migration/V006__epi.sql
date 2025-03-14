CREATE TABLE tb_epi(
    id UUID PRIMARY KEY NOT NULL,
    nome varchar(80) NOT NULL,
    certificado_aprovacao varchar(80),
    quantidade INT,
    valor_unitario DECIMAL(10,2),
    data_fabricacao DATE,
    data_vencimento DATE,
    lote varchar(80),
    tag VARCHAR(80),
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL
);