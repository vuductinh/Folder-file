package vn.com.gsoft.tcom.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "File")
public class File{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "File_Path")
    private String filePath;
    @Column(name = "Id_Folder")
    private Long idFolder;
    @CreatedDate
    @Column(name="Created")
    private Date created;
    @LastModifiedDate
    @Column(name="Modified")
    private Date modified;
    @Transient
    private Folder folder;
}
