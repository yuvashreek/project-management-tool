package com.myapp.projectmtool.repositories;

import com.myapp.projectmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String ProjectId);


}
