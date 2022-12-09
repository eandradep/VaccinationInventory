package com.kruger.vaccination.inventory.models.services.vaccinetype;

import com.kruger.vaccination.inventory.models.entity.Vaccine;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IVaccineService {

    @Transactional(readOnly = true)
    List<Vaccine> findAll();

    @Transactional()
    Vaccine saveVaccine(Vaccine vaccine);

}
