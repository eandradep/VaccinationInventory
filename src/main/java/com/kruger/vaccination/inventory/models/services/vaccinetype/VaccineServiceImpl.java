package com.kruger.vaccination.inventory.models.services.vaccinetype;

import com.kruger.vaccination.inventory.models.dao.IVaccineDAO;
import com.kruger.vaccination.inventory.models.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineServiceImpl implements IVaccineService {

    @Autowired
    private IVaccineDAO iVaccineDAO;

    @Override
    public List<Vaccine> findAll() {
        return (List<Vaccine>) iVaccineDAO.findAll();
    }

    @Override
    public Vaccine saveVaccine(Vaccine vaccine) {
        return iVaccineDAO.save(vaccine);
    }
}
