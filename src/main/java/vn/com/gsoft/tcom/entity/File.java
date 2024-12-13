package vn.com.gsoft.tcom.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = File.TABLE_NAME)
public class File extends BaseEntity{
    public static final String TABLE_NAME = "File";
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
