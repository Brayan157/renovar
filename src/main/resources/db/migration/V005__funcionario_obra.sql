CREATE TABLE tb_funcionario_obra(
    funcionario_id INT NOT NULL,
    obra_id INT NOT NULL,
    data_inicio DATE,
    data_fim DATE,
    descricao VARCHAR(255),
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_funcionario_obra_funcionario FOREIGN KEY(funcionario_id) REFERENCES tb_funcionario(id),
    CONSTRAINT fk_funcionario_obra_obra FOREIGN KEY(obra_id) REFERENCES tb_obra(id),
    primary key (funcionario_id, obra_id)

);