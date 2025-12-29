-- liquibase formatted sql

-- changeset longnd:053
INSERT INTO lookup_type (code, name, description)
VALUES (
           'IP_VERSION',
           'IP Version',
           'Phiên bản giao thức IP'
       );

-- changeset longnd:054
INSERT INTO lookup_value
(
    lookup_type_id,
    code,
    name,
    description,
    numeric_value,
    sort_order,
    is_active
)
VALUES
    (
        (SELECT id FROM lookup_type WHERE code = 'IP_VERSION'),
        'VERSION_IPV4',
        'IPv4',
        'Internet Protocol Version 4',
        4,
        1,
        TRUE
    ),
    (
        (SELECT id FROM lookup_type WHERE code = 'IP_VERSION'),
        'VERSION_IPV6',
        'IPv6',
        'Internet Protocol Version 6',
        6,
        2,
        TRUE
    );
