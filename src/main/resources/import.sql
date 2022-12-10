-- SCRIPT QUE SE USA PARA PODER TENER DATOS INICIALES EN EL PROYECTO,
-- EMPLOYEE INFORMATION
INSERT INTO employee (employee_email, employee_identification, employee_last_name, employee_name, employee_is_vaccinate) VALUES ('eandradep@est.ups.edu.ec', '0106126154', 'ANDRADE PRIETO', 'EDISON BLADIMIR', true);
INSERT INTO employee (employee_email, employee_identification, employee_last_name, employee_name, employee_is_vaccinate) VALUES ('eandradep1@est.ups.edu.ec', '0106126155', 'ANDRADE PRIETO', 'EDISON VLADIMIR', false);
INSERT INTO employee (employee_email, employee_identification, employee_last_name, employee_name, employee_is_vaccinate) VALUES ('eandradep2@est.ups.edu.ec', '0106126156', 'ANDRADE PRIETO', 'EDISON WLADIMIR', false);
INSERT INTO employee (employee_email, employee_identification, employee_last_name, employee_name, employee_is_vaccinate) VALUES ('eandradep3@est.ups.edu.ec', '0106126157', 'ANDRADE PRIETO', 'JAIME PATRICIO', true);
-- VACCINE TYPE
INSERT INTO vaccine (vaccine_name) VALUES ('SPUTNIK');
INSERT INTO vaccine (vaccine_name) VALUES ('ASTRAZENECA');
INSERT INTO vaccine (vaccine_name) VALUES ('PFIZER');
INSERT INTO vaccine (vaccine_name) VALUES ('JHONSON&JHONSN');
-- EMPLOYEE IMMUNIZATION RECORD
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (1, '2022-08-08', 1, 1);
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (2, '2022-09-15', 2, 1);
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (3, '2022-10-15', 3, 1);
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (4, '2022-11-15', 4, 1);
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (5, '2022-12-15', 2, 1);
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (1, '2022-09-10', 2, 4);
INSERT INTO public.immunization_record (doses_number, vaccination_date, vaccine_id, employee_id) VALUES (2, '2022-11-10', 3, 4);

