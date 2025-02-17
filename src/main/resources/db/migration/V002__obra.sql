CREATE TABLE tb_obra(
    id UUID PRIMARY KEY NOT NULL,
    empresa_prestante VARCHAR(80) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    status varchar(45) NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    endereco_id UUID NOT NULL,
    CONSTRAINT fk_obra_endereco FOREIGN KEY(endereco_id) REFERENCES tb_endereco(id)
);