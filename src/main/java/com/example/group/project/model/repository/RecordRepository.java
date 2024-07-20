package com.example.group.project.model.repository;

import com.example.group.project.model.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByNameIgnoreCase(String recordName);
    List<Record> findByArtistIgnoreCase(String artistName);
    List<Record> findByNameAndArtistIgnoreCase(String recordName, String Artist);
}
