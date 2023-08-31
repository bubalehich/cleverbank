create table public.account_type
(
    id   bigserial primary key,
    name varchar(255) unique
);

create table public.banks
(
    id         bigserial primary key,
    bic_number varchar(255) not null unique,
    country    varchar(255),
    name       varchar(255) not null
);

create table public.currencies
(
    id     bigserial primary key,
    handle varchar(255) not null unique,
    name   varchar(255)
);

create table public.customers
(
    is_active boolean not null,
    id        bigserial primary key,
    name      varchar(255),
    surname   varchar(255)
);

create table public.accounts
(
    balance      numeric(38, 2),
    is_active    boolean                     not null,
    bank_id      bigint                      not null
        constraint fkb78evw9x9jyy66ld572kl8rgx references public.banks,
    currency_id  bigint
        constraint fks08d0ccyak63pou9tfk093dbk references public.currencies,
    customer_id  bigint
        constraint fkn6x8pdp50os8bq5rbb792upse references public.customers,
    id           bigserial primary key,
    opening_date timestamp(6) with time zone not null,
    type_id      bigint
        constraint fk48mkt1lrni55hidaw233s0ytm references public.account_type,
    number       varchar(255)                not null unique
);

create table public.transaction_types
(
    id   bigserial primary key,
    name varchar(255) unique
);

create table public.transactions
(
    amount      numeric(38, 2),
    date        timestamp(6) with time zone,
    id          bigserial primary key,
    receiver_id bigint
        constraint fkjord7to517f34oa9ni6puyt85 references public.accounts,
    sender_id   bigint
        constraint fkep3ko5p4fdvnw79p1uhw2q6nf references public.accounts,
    type_id     bigint
        constraint fk8rugypr4phjn6n2i4eb3ud2ox references public.transaction_types,
    number      uuid not null unique,
    payload     varchar(255)
);
