package t2406e_group1.bookshopspringboot.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "entity_user")
@Getter
@Setter
public class EntityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_join")
    private Date dateJoin;

    @Column(name = "birth_day")
    private Date birthDay;

    @Column(name = "address")
    private String address;

    @Column(name = "roles")
    private String roles;

    public EntityUser() {
        this.name = "(chưa xác định)";
        this.fullName = "(chưa xác định)";
        this.roles = "ROLE_USER";
    }

    public Boolean InputError() {
        boolean ipe = false;
        if (this.name.length() < 2) {
            ipe = true;
            System.out.println("\n Lỗi->Tên phải từ 2 kí tự trở lên: ");
        }
        if (this.name.length() > 22) {
            ipe = true;
            System.out.println("\n Lỗi->Tên phải không quá 22 kí tự. ");
        }
        if (this.fullName != null && this.fullName.length() < 2) {
            ipe = true;
            System.out.println("\n Lỗi->Tên đầy đủ phải từ 2 kí tự trở lên: ");
        }
        if (this.fullName != null && this.fullName.length() > 22) {
            ipe = true;
            System.out.println("\n Lỗi->Tên đầy đủ phải không quá 22 kí tự. ");
        }
        return ipe;
    }

    public List<String> getRoleList() {
        if (this.roles == null || this.roles.isEmpty()) {
            return new ArrayList<>();
        }
        return List.of(this.roles.split(","));
    }
}