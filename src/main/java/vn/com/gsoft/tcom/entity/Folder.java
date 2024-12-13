package vn.com.gsoft.tcom.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Folder")
public class Folder{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Id_Folder")
    private Long idFolder;
    @Transient
    private List<Folder> children;
    @Transient
    private List<File> files;
}
