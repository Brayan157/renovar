CREATE TABLE tb_ferramenta_funcionario(
    funcionario_id INT NOT NULL,
    ferramenta_id INT NOT NULL,
    quantidade INT NOT NULL,
    data_entrega DATE NOT NULL,
    data_devolucao DATE,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES tb_funcionario(id),
    FOREIGN KEY (ferramenta_id) REFERENCES tb_ferramenta(id),
    PRIMARY KEY (funcionario_id, ferramenta_id)
);