INSERT INTO student
(first_name, last_name, program_name, program_year, program_coop, program_internship)
VALUES
('Harry', 'Potter', 'Computer Programmer', 1, true, false),
('Ronald', 'Weasley', 'Systems Technician', 1, false, true),
('George', 'Weasley', 'Systems Technician', 2, false, true),
('Fred', 'Weasley', 'Computer Programmer', 2, false, true),
('Ginny', 'Weasley', 'Engineering Technician', 1, false, true),
('Hermione', 'Granger', 'Systems Technology', 1, false, false),
('Draco', 'Malfoy', 'Engineering Technician', 1, true, true);

/* all these passwords are "sesame" */
INSERT INTO app_user
(user_name, password)
VALUES
('marge','$2a$10$bxGtVIu12/dXFQ8I1VrCmeFap8AXK.8EFgp.NRgaGt5no27uZd8Ty'),
('homer','$2a$10$5y39gonhJWNtUXFHi3gLaumMYLKmK/O4Jshi4/IlhryYNxhEFSNuy'),
('bart','$2a$10$WFceIBbBe2ynUC6ckJltOeI9qNgKSqGzE/PqD2BbxBHSVZyscOF8O'),
('lisa','$2a$10$/0le0donOsBt.kSva6CNNeNXRjm83m.VQeEsWHyY9ORQwJeGN/DAa');

INSERT INTO app_role
(role_name)
VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO app_user_role
(user_id, role_id)
VALUES
(1,1),
(2,1),
(3,2),
(4,2);
