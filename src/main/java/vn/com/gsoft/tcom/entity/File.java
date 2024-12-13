package vn.com.gsoft.tcom.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Transient
    private Folder folder;
}
