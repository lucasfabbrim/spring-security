INSERT INTO roles (role_id, name)
SELECT 1, 'admin'
    WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE role_id = 1
);

INSERT INTO roles (role_id, name)
SELECT 2, 'member'
    WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE role_id = 2
);
