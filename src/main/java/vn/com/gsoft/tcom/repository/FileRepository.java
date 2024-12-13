package vn.com.gsoft.tcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.tcom.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}
