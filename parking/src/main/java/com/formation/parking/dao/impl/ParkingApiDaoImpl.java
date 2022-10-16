package com.formation.parking.dao.impl;

import com.formation.parking.dao.ParkingApiDao;
import com.formation.parking.dao.entity.ReponseParkingApiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ParkingApiDaoImpl implements ParkingApiDao {

    private static final String URL_API_OPEN_DATA = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_parkings-publics-nantes-disponibilites&q=&rows=-1&facet=grp_nom&facet=grp_statut";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ReponseParkingApiEntity getListParking() {
        return restTemplate.getForEntity(URL_API_OPEN_DATA, ReponseParkingApiEntity.class).getBody();
    }
}
