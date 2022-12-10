-- EMPLOYEE INFORMATION
INSERT INTO employee (employee_email, employee_identification, employee_last_name, employee_name) VALUES ('eandradep@est.ups.edu.ec', '0106126154', 'ANDRADE PRIETO', 'EDISON BLADIMIR');
-- VACCINE TYPE
INSERT INTO vaccine (vaccine_name) VALUES ('SPUTNIK');
INSERT INTO vaccine (vaccine_name) VALUES ('ASTRAZENECA');
INSERT INTO vaccine (vaccine_name) VALUES ('PFIZER');
INSERT INTO vaccine (vaccine_name) VALUES ('JHONSON&JHONSN');
-- EMPLOYEE IMMUNIZATION RECORD
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (1, '2022-12-08', 1, 1);

