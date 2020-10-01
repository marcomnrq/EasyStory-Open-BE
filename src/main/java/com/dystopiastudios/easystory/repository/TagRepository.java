package com.dystopiastudios.easystory.repository;

import com.dystopiastudios.easystory.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository es algo que utiliza Spring para poder catalogar ciertos componentes
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    //Tag repository va a ser alias de JpaRepository
    //Tipo de los primary key, identificadores de entidades

}
