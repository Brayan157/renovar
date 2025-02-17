CREATE TABLE tb_ferramenta_funcionario(
    funcionario_id UUID NOT NULL,
    ferramenta_id UUID NOT NULL,
    quantidade INT NOT NULL,
    data_entrega DATE NOT NULL,
    data_devolucao DATE,
    status_ferramenta VARCHAR(80) NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    creation_date_id UUID NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES tb_funcionario(id),
    FOREIGN KEY (ferramenta_id) REFERENCES tb_ferramenta(id),
    FOREIGN KEY (creation_date_id) REFERENCES tb_creation_date(id),
    PRIMARY KEY (funcionario_id, ferramenta_id, creation_date_id)
);