package com.formation.parking.services.impl;

import com.formation.parking.dao.ParkingApiDao;
import com.formation.parking.dao.entity.RecordEntity;
import com.formation.parking.dao.entity.ReponseParkingApiEntity;
import com.formation.parking.models.Parking;
import com.formation.parking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    ParkingApiDao parkingApiDao;

    @Override
    public List<Parking> getListParking() {
        ReponseParkingApiEntity reponse = parkingApiDao.getListParking();
        return transformeEntityToModel(reponse);
    }

    private List<Parking> transformeEntityToModel(ReponseParkingApiEntity reponse) {
        List<Parking> resultat = new ArrayList<Parking>();
        for(RecordEntity record : reponse.getRecords())
        {
            Parking parking = new Parking();
            parking.setNom(record.getFields().getGrpNom());
            parking.setStatut(getLibelleStatut(record));
            parking.setNbPlacesDispo(record.getFields().getGrpDisponible());
            parking.setNbPlacesTotal(record.getFields().getGrpExploitation());
            parking.setHeureMaj(getHeureMaj(record));
            resultat.add(parking);
        }

        return resultat;
    }

    private String getLibelleStatut(RecordEntity record){
        String retVal = "";

        switch (record.getFields().getGrpStatut())
        {
            case "1": retVal = "Fermé";
                      break;
            case "2": retVal = "Abonné";
                break;
            case "5": retVal = "Ouvert";
                break;
            default: retVal = "Données insdisponibles";
                break;
        }
        return retVal;
    }

    private String getHeureMaj(RecordEntity record) {
        OffsetDateTime dateMaj = OffsetDateTime.parse(record.getFields().getGrpHorodatage());
        dateMaj.withOffsetSameInstant(ZoneOffset.of("+02:00"));
        return dateMaj.getHour() + "h" + dateMaj.getMinute();
    }

}
