create table produtos(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    preco double not null,
    descricao varchar(250) not null,
    data date not null,
    id_usuario bigint not null,
    foreign key (id_usuario) references usuarios(id),

    primary key(id)
);