CREATE TABLE tb_ferramenta_obra(
    ferramenta_id UUID NOT NULL,
    obra_id UUID NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    data_entrega DATE NOT NULL,
    data_saida DATE,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    creation_date_id UUID NOT NULL,
    foreign key (ferramenta_id) references tb_ferramenta(id),
    foreign key (obra_id) references tb_obra(id),
    foreign key (creation_date_id) references tb_creation_date(id),
    primary key (ferramenta_id, obra_id, creation_date_id)
);