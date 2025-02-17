CREATE TABLE tb_endereco(
    id UUID PRIMARY KEY NOT NULL,
    rua VARCHAR(80),
    numero varchar(80),
    bairro VARCHAR(80),
    cidade VARCHAR(80) NOT NULL,
    estado VARCHAR(80) NOT NULL,
    cep VARCHAR(20) NOT NULL,
    complemento VARCHAR(80),
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL
);