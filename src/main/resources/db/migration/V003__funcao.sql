CREATE TABLE tb_funcao(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    funcao VARCHAR(80) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL
);