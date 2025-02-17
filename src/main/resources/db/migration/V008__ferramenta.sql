CREATE TABLE tb_ferramenta(
    id UUID PRIMARY KEY NOT NULL,
    nome varchar(80) NOT NULL,
    data_compra DATE,
    status VARCHAR(80),
    valor_unitario DECIMAL(10,2),
    quantidade INT,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL
);