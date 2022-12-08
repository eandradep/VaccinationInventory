package com.kruger.vaccination.inventory.models.dao;

import com.kruger.vaccination.inventory.models.entity.Vaccine;
import org.springframework.data.repository.CrudRepository;

public interface IVaccineDAO extends CrudRepository<Vaccine, Long> {
}
