CREATE TABLE tb_epis_funcionario(
    id_funcionario UUID NOT NULL,
    id_epi UUID NOT NULL,
    id_creation_date UUID NOT NULL,
    quantidade INT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    data_entrega DATE NOT NULL,
    data_devolucao DATE,
    status_epi VARCHAR(80) NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (id_funcionario) REFERENCES tb_funcionario(id),
    FOREIGN KEY (id_epi) REFERENCES tb_epi(id),
    FOREIGN KEY (id_creation_date) REFERENCES tb_creation_date(id),
    PRIMARY KEY (id_funcionario, id_epi, id_creation_date)
);