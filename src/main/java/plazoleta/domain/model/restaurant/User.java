package plazoleta.domain.model.restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import plazoleta.adapters.driven.jpa.msql.entity.RolEntity;

import java.time.LocalDate;

public class User {
    private int idUser;
    private String name;
    private String lastName;
    private String identificationDocument;
    private String phone;
    private LocalDate birthDate;
    private String gmail;
    private String password;
    private RolEntity idRol;

    public User (){};
    public User(int idUser,
                String name,
                String lastName,
                String identificationDocument,
                String phone,
                LocalDate birthDate,
                String gmail,
                String password,
                RolEntity idRol
    ) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.identificationDocument = identificationDocument;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gmail = gmail;
        this.password = password;
        this.idRol = idRol;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationDocument() {
        return identificationDocument;
    }

    public void setIdentificationDocument(String identificationDocument) {
        this.identificationDocument = identificationDocument;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolEntity getIdRol() {
        return idRol;
    }

    public void setIdRol(RolEntity idRol) {
        this.idRol = idRol;
    }
}
