package vn.com.gsoft.tcom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Folder.TABLE_NAME)
public class Folder extends BaseEntity {
    public static final String TABLE_NAME = "Folder";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Id_Folder")
    private Long idFolder;
    @Transient
    private List<File> files;

}
