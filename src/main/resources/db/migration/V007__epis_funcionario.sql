CREATE TABLE epis_funcionario(
    id_funcionario INT NOT NULL,
    id_epi INT NOT NULL,
    quantidade INT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (id_funcionario) REFERENCES tb_funcionario(id),
    FOREIGN KEY (id_epi) REFERENCES tb_epi(id),
    PRIMARY KEY (id_funcionario, id_epi)
);